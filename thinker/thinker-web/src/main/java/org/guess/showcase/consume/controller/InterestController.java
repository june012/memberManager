package org.guess.showcase.consume.controller;

import org.guess.core.Constants;
import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.InterestRecord;
import org.guess.showcase.consume.service.InterestService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/interest")
public class InterestController extends BaseController<InterestRecord>{
    {
        editView = "/consume/interest/edit";
        listView = "/consume/interest/list";
        showView = "/consume/interest/show";
    }

    @Autowired
    private InterestService interestService;

    @Autowired
    private MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(InterestController.class);


    @RequestMapping("/batchInterest")
    private void batchInterest(@DateTimeFormat(pattern = "yyyy-MM-dd") Date batchDate,
                               double rate
                               ){
        try {
            List<Member> members = memberService.getAll();
            logger.info("定时任务-->生成利息 共{}个会员"+members.size());
            int count = 0;
            for(Member member:members){
                if (member.getStatus().equals(Constants.MEMBER_STATUS_UNACTIVATED)||member.getStatus().equals(Constants.MEMBER_STATUS_DELETE)){
                    continue;
                }
                if (null == member.getPrincipal()||member.getPrincipal().compareTo(new BigDecimal("0"))!=1){
                    continue;
                }
                InterestRecord interestRecord = new InterestRecord();
                interestRecord.setDate(new Date());
                interestRecord.setRate(rate);
                interestRecord.setInterestAdd(member.getPrincipal().multiply(new BigDecimal(rate)));
                member.setAccount(member.getAccount().add(interestRecord.getInterestAdd()));
                interestRecord.setAccountAfter(member.getAccount());
                interestRecord.setPrincipal(member.getPrincipal());
                interestRecord.setUserId(member.getId());
                memberService.save(member);
                interestService.save(interestRecord);
                count++;
            }
            logger.info("共生成利息{}条,其中{}个会员失效(未激活,已删除,余额为0)",count,members.size()-count);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
