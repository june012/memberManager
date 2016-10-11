package org.guess.batch;

import org.guess.facility.DefinedConstant;
import org.guess.showcase.consume.model.FillRecord;
import org.guess.showcase.consume.model.InterestRecord;
import org.guess.showcase.consume.service.FillService;
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
 * Created by wan.peng on 2016/10/10.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private FillService fillService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * 计算每日利息
     * @param batchDate
     * @param rate
     */
    @RequestMapping("/batchInterest")
    private void batchInterest(@DateTimeFormat(pattern = "yyyy-MM-dd") Date batchDate,
                               double rate){
        try {
            List<Member> members = memberService.getAll();
            logger.info("定时任务-->生成利息 共{}个会员"+members.size());
            int count = 0;
            for(Member member:members){
                if (member.getStatus().equals(DefinedConstant.MEMBER_STATUS_UNACTIVATED)||member.getStatus().equals(DefinedConstant.MEMBER_STATUS_DELETE)){
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

    /**
     * 更新可消费金额
     * @param batchDate
     */
    @RequestMapping("/updateCanBeConsumed")
    private void updateCanBeConsumed(@DateTimeFormat(pattern = "yyyy-MM-dd") Date batchDate){
        List<FillRecord> fillRecords = fillService.findBy("isHandled", DefinedConstant.FILL_HANDLE_NO);
        for(FillRecord fillRecord : fillRecords){
            if (fillRecord.getDrawTime().getTime()-batchDate.getTime()<=0){
                Member member = memberService.findUniqueBy("id",fillRecord.getUserid());
                member.setCanBeConsumed(member.getCanBeConsumed().add(member.getAward()).add(member.getInterest()).add(member.getPrincipal()));
                member.setAward(new BigDecimal("0"));
                member.setPrincipal(member.getPrincipal().subtract(fillRecord.getMoney()));
                member.setInterest(new BigDecimal("0"));
                fillRecord.setIsHandled(DefinedConstant.FILL_HANDLE_YES);
                try {
                    memberService.save(member);
                    fillService.save(fillRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
