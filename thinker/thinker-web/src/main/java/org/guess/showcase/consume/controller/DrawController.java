package org.guess.showcase.consume.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.facility.DefinedConstant;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.model.DrawRecord;
import org.guess.showcase.consume.service.ConsumeLogService;
import org.guess.showcase.consume.service.DrawService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.User;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/draw")
public class DrawController extends BaseController<DrawRecord>{
    {
        editView = "/consume/draw/edit";
        listView = "/consume/draw/list";
        showView = "/consume/draw/show";
    }

    @Autowired
    private DrawService drawService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumeLogService consumeLogService;

    private static final Logger logger = LoggerFactory.getLogger(DrawController.class);

    @Override
    public String create(@Valid DrawRecord object) throws Exception {
        BigDecimal money = object.getMoney();
        boolean log = false;
        Member member = memberService.findUniqueBy("id", object.getUserid());
        if(member == null){
            System.out.println("无此会员");
        }
        if(member.getAccount().compareTo(money)==-1){
            logger.info("余额不足");
            return null;
        }
        if(object.getId() == 0){
            log=true;
            if(member.getAccount().subtract(member.getPrincipal()).compareTo(money)>=0){
                member.setAccount(member.getAccount().subtract(money));
            }else{
                member.setAccount(member.getAccount().subtract(money));
                member.setPrincipal(member.getAccount());
            }
            object.setCreateTime(new Date());
        }else{
            DrawRecord drawRecord = drawService.findUniqueBy("id", object.getId());
            if(member.getAccount().add(drawRecord.getMoney()).compareTo(object.getMoney()) == -1){
                logger.info("余额不足");
                return null;
            }
            object.setCreateTime(drawRecord.getCreateTime());
            member.setAccount(member.getAccount().add(drawRecord.getMoney()).subtract(money));
        }
        memberService.save(member);
        object.setAccountAfter(member.getAccount());
        drawService.save(object);
        if(log){//新建奖金记录则生成日志
            ConsumeLog consumeLog = new ConsumeLog();
            consumeLog.setCreateTime(new Date());
            consumeLog.setAccount(object.getMoney());
            consumeLog.setTypeId(object.getId());
            consumeLog.setMemberId(member.getId());
            consumeLog.setConsumeType(DefinedConstant.CONSUME_TYPE_DRAW);
            consumeLogService.save(consumeLog);
        }
        return "redirect:/consume/draw/list";
    }

    @Override
    public Map<String, Object> page(Page<DrawRecord> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from DrawRecord a where 1=1 ";
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
                }
            }
        }
        return drawService.findPage(page,hql).returnMap();
    }
}
