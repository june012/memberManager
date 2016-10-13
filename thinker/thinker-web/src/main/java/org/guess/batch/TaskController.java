package org.guess.batch;

import org.guess.facility.DefinedConstant;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.model.FillRecord;
import org.guess.showcase.consume.model.InterestRecord;
import org.guess.showcase.consume.service.ConsumeLogService;
import org.guess.showcase.consume.service.FillService;
import org.guess.showcase.consume.service.InterestService;
import org.guess.showcase.consume.service.RateLogService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Component
@Lazy(value=false)
public class TaskController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private FillService fillService;

    @Autowired
    private RateLogService rateLogService;

    @Autowired
    private ConsumeLogService consumeLogService;


    private final long rateId = Long.valueOf("1");

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * 计算每日利息
     */
    @Scheduled(cron = "0 0 15 * * ?")
    private void batchInterest() throws Exception {
        logger.info("---计算每日利息开始---");
        Date batchDate = new Date();
        double rate = rateLogService.findUniqueBy("id",rateId).getRate();
        List<FillRecord> fillRecords = fillService.findBy("isHandled", DefinedConstant.FILL_HANDLE_NO);
        Map<Member,BigDecimal> memberMap = new HashMap<Member, BigDecimal>();

        for(FillRecord fillRecord:fillRecords){
            Member member = memberService.findUniqueBy("id", fillRecord.getUserid());
            if(member==null){
                logger.info("有充值记录,但是找不到该会员 检查会员{}是否已销卡",fillRecord.getUserid());
                continue;
            }

            BigDecimal addInterest =fillRecord.getMoney().multiply(new BigDecimal(rate));
            fillRecord.setIntertest(fillRecord.getIntertest().add(addInterest));
            if(memberMap.get(member)==null){
                memberMap.put(member,addInterest);
            }else{
                memberMap.get(member).add(addInterest);
            }
            fillService.save(fillRecord);
        }
        Set<Member> members1 = memberMap.keySet();

        int count = 0;
        for(Member m:members1){
            m.setInterest(m.getInterest().add(memberMap.get(m)));
            m.setAccount(m.getAccount().add(memberMap.get(m)));
            memberService.save(m);
            //生成利息记录
            InterestRecord interestRecord = new InterestRecord();
            interestRecord.setDate(batchDate);
            interestRecord.setRate(rate);
            interestRecord.setInterestAdd(memberMap.get(m));
            interestRecord.setAccountAfter(m.getAccount());
            interestRecord.setPrincipal(m.getPrincipal());
            interestRecord.setUserId(m.getId());
            interestService.save(interestRecord);

            //生成操作日志
            ConsumeLog consumeLog = new ConsumeLog();
            consumeLog.setCreateTime(new Date());
            consumeLog.setMemberId(m.getId());
            consumeLog.setAccount(interestRecord.getInterestAdd());
            consumeLog.setTypeId(interestRecord.getId());
            consumeLog.setConsumeType(DefinedConstant.CONSUME_TYPE_INTEREST);
            consumeLogService.save(consumeLog);
            count++;
        }
        logger.info("定时任务-->共生成利息{}条",count);
        logger.info("---计算每日利息结束---");

    }

    /**
     * 更新可消费金额
     */
    @Scheduled(cron = "0 10 15 * * ?")
    private void updateCanBeConsumed(){
        logger.info("---更新可消费金额开始---");
        Date batchDate = new Date();
        List<FillRecord> fillRecords = fillService.findBy("isHandled", DefinedConstant.FILL_HANDLE_NO);
        for(FillRecord fillRecord : fillRecords){
            if (fillRecord.getDrawTime().getTime()-batchDate.getTime()<=0){
                Member member = memberService.findUniqueBy("id",fillRecord.getUserid());
                member.setCanBeConsumed(member.getCanBeConsumed().add(fillRecord.getIntertest()).add(fillRecord.getMoney()));
                member.setPrincipal(member.getPrincipal().subtract(fillRecord.getMoney()));
                fillRecord.setIsHandled(DefinedConstant.FILL_HANDLE_YES);
                try {
                    memberService.save(member);
                    fillService.save(fillRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("---更新可消费金额结束---");
    }
}
