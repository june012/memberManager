package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.CashRecord;
import org.guess.showcase.consume.service.CashService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/cash")
public class CashController extends BaseController<CashRecord>{
    {
        editView = "/consume/cash/edit";
        listView = "/consume/cash/list";
        showView = "/consume/cash/show";
    }
    @Autowired
    private CashService cashService;

    @Autowired
    private MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(CashController.class);

    @Override
    public String create(@Valid CashRecord object) throws Exception {
        BigDecimal money = object.getMoney();
        System.out.println(money);
        Member member = memberService.findUniqueBy("id", object.getUserid());
        if(member == null){
            logger.error("无此会员");
            return null;
        }
        if(member.getAccount().compareTo(money)==-1){
            logger.info("余额不足");
            return null;
        }
        if(object.getId() == 0){
            if(member.getAccount().subtract(member.getPrincipal()).compareTo(money)>=0){
                member.setAccount(member.getAccount().subtract(money));
            }else{
                member.setAccount(member.getAccount().subtract(money));
                member.setPrincipal(member.getAccount());
            }
            object.setCreateTime(new Date());
        }else{
            CashRecord cashRecord = cashService.findUniqueBy("id", object.getId());
            if(member.getAccount().add(cashRecord.getMoney()).compareTo(object.getMoney()) == -1){
                logger.info("余额不足");
                return null;
            }
            object.setCreateTime(cashRecord.getCreateTime());
            member.setAccount(member.getAccount().add(cashRecord.getMoney()).subtract(money));
        }
        memberService.save(member);
        return super.create(object);
    }
}
