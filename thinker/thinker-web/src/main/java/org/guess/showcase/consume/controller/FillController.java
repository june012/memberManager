package org.guess.showcase.consume.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.facility.DefinedConstant;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.model.FillRecord;
import org.guess.showcase.consume.service.ConsumeLogService;
import org.guess.showcase.consume.service.FillService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.User;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/fill")
public class FillController extends BaseController<FillRecord>{
    {
        editView = "/consume/fill/edit";
        listView = "/consume/fill/list";
        showView = "/consume/fill/show";
    }

    @Autowired
    private FillService fillService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumeLogService consumeLogService;

    /**
     * 提现周期
     */
    private final int circle= 2;

    private static final Logger logger = LoggerFactory.getLogger(FillController.class);


    @Override
    public String create(@Valid FillRecord object) throws java.lang.Exception {
        BigDecimal money = object.getMoney();
        boolean log = false;
        Member member = memberService.findUniqueBy("id", object.getUserid());
        if(member == null){
            logger.info("无此会员");
            return null;
        }
        if(object.getIsHandled()==null){
            object.setIsHandled(DefinedConstant.FILL_HANDLE_NO);
        }

        if(object.getId() == 0){
            log=true;
            member.setAccount(member.getAccount().add(money));
            member.setPrincipal(member.getPrincipal().add(money));
            object.setCreateTime(new Date());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(object.getCreateTime());
            calendar.add(Calendar.YEAR,circle);
            object.setDrawTime(calendar.getTime());
        }else{
            FillRecord fillRecord = fillService.findUniqueBy("id", object.getId());
            member.setAccount(member.getAccount().subtract(fillRecord.getMoney()).add(money));
            member.setPrincipal(member.getPrincipal().subtract(fillRecord.getMoney().add(money)));
            object.setDrawTime(fillRecord.getDrawTime());
            object.setCreateTime(fillRecord.getCreateTime());
        }

        memberService.save(member);
        object.setAccountAfter(member.getAccount());
        object.setPrincipalAfter(member.getPrincipal());
        fillService.save(object);

        if(log){//新建奖金记录则生成日志
            ConsumeLog consumeLog = new ConsumeLog();
            consumeLog.setCreateTime(new Date());
            consumeLog.setAccount(object.getMoney());
            consumeLog.setMemberId(member.getId());
            consumeLog.setTypeId(object.getId());
            consumeLog.setConsumeType(DefinedConstant.CONSUME_TYPE_FILL);
            consumeLogService.save(consumeLog);
        }
        return "redirect:/consume/fill/list";
    }

    @Override
    public Map<String, Object> page(Page<FillRecord> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from FillRecord a where 1=1 ";
        String search_EQL_userid = request.getParameter("search_EQL_userid");
        String search_EQD_createTime = request.getParameter("search_EQD_createTime");
        String search_EQS_phone = request.getParameter("search_EQS_phone");

        if(search_EQL_userid!= null){
            hql+= " and a.userid="+search_EQL_userid;
        }

        if(search_EQD_createTime!= null){
            hql+= " and date_format(a.createTime,'%Y-%c-%d')='"+search_EQD_createTime+"'";
        }
        if(search_EQS_phone != null){
            Member phone = memberService.findUniqueBy("phone", search_EQS_phone);
            if(phone!=null){
                hql+=" and a.userid="+phone.getId();
            }else {
                hql+=" and 1=2";
            }
        }
        if(currentUser.getStoreId() == 0){
            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                String memberIds = memberService.findMemberIds(search_eql_storeId);
                if (memberIds!=null){
                    hql+=" and a.userid in("+memberIds+")";
                }else{
                    hql+=" and 1=2";
                }
            }
        }
        return fillService.findPage(page,hql).returnMap();
    }

    @RequestMapping("isAvailable")
    public @ResponseBody
    boolean isLoginIdAvailable(@RequestParam("userid") String userid) {
        Member member = memberService.findUniqueBy("id", Long.valueOf(userid));
        if(member==null){
            logger.info("该会员不存在:"+userid);
            return false;
        }
        if(member.getPrincipal()==new BigDecimal("0")){
            return true;
        }
        return false;
    }
}
