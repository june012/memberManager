package org.guess.showcase.consume.controller;

import com.google.gson.Gson;
import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.facility.DefinedConstant;
import org.guess.facility.Exception.DefinedException;
import org.guess.facility.Exception.ErrorCode;
import org.guess.showcase.consume.model.AwardRecord;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.service.AwardService;
import org.guess.showcase.consume.service.ConsumeLogService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/award")
public class AwardController extends BaseController<AwardRecord>{
    {
        editView = "/consume/award/edit";
        listView = "/consume/award/list";
        showView = "/consume/award/show";
    }

    @Autowired
    private AwardService awardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumeLogService consumeLogService;


    private static final Logger logger = LoggerFactory.getLogger(AwardController.class);

    @Override
    public String create(@Valid AwardRecord object) throws Exception {
        BigDecimal awardMoney = object.getAwardMoney();
        Member member = memberService.findUniqueBy("id", object.getMemberId());
        if(member == null){
           throw new DefinedException(ErrorCode.NOT_EXSIST_THIS_MEMBER.getCode(),ErrorCode.NOT_EXSIST_THIS_MEMBER.getMessage());
        }
        boolean log = false;
        if(object.getId() == 0){
            log=true;
            member.setAward(member.getAward().add(awardMoney));
            member.setAccount(member.getAccount().add(awardMoney));
            object.setDate(new Date());
        }else{
            AwardRecord awardRecord = awardService.findUniqueBy("id", object.getId());
            member.setAward(member.getAward().subtract(awardRecord.getAwardMoney()).add(awardMoney));
            member.setAccount(member.getAccount().subtract(awardRecord.getAwardMoney()).add(awardMoney));
            object.setDate(awardRecord.getDate());
        }
        memberService.save(member);
        object.setAwardAfter(member.getAward());
        awardService.save(object);
        if(log){//新建奖金记录则生成日志
            ConsumeLog consumeLog = new ConsumeLog();
            consumeLog.setCreateTime(new Date());
            consumeLog.setMemberId(member.getId());
            consumeLog.setAccount(object.getAwardMoney());
            consumeLog.setTypeId(object.getId());
            consumeLog.setConsumeType(DefinedConstant.CONSUME_TYPE_AWARD);
            new Gson().toJson(consumeLog);
            consumeLogService.save(consumeLog);
        }
        return "redirect:/consume/award/list";
    }


    @Override
    public Map<String, Object> page(Page<AwardRecord> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from AwardRecord a where 1=1 ";
        String search_eql_memberId = request.getParameter("search_EQL_memberId");
        String search_eqs_awardType = request.getParameter("search_EQS_awardType");
        String search_eqd_date = request.getParameter("search_EQD_date");
        String search_EQS_phone = request.getParameter("search_EQS_phone");
        if(search_eql_memberId!= null){
            hql+= " and a.memberId="+search_eql_memberId;
        }
        if(search_eqs_awardType!= null){
            hql+= " and a.awardType='"+search_eqs_awardType+"'";
        }
        if(search_eqd_date!= null){
            hql+= " and date_format(a.date,'%Y-%c-%d')='"+search_eqd_date+"'";
            System.out.println(search_eqd_date);
        }
        if(search_EQS_phone != null){
            Member phone = memberService.findUniqueBy("phone", search_EQS_phone);
            if(phone!=null){
                hql+=" and a.memberId="+phone.getId();
            }else{
                hql+=" and 1=2";
            }
        }
        if(currentUser.getStoreId() == 0){
            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                List<Member> members = memberService.findBy("storeId", Long.valueOf(search_eql_storeId));
                String memberIds="";
                for(Member m :members){
                    memberIds+=m.getId()+",";
                }
                String substring = "";
                if(members.size()>0){
                    substring = memberIds.substring(0, memberIds.length() - 1);
                    System.out.println(substring);
                    hql+=" and a.memberId in("+substring+")";
                }
            }
        }
        return awardService.findPage(page,hql).returnMap();
    }


}
