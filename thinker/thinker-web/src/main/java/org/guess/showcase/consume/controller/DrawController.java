package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.DrawRecord;
import org.guess.showcase.consume.service.DrawService;
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

    private static final Logger logger = LoggerFactory.getLogger(DrawController.class);

    @Override
    public String create(@Valid DrawRecord object) throws Exception {
        BigDecimal money = object.getMoney();
        Member member = memberService.findUniqueBy("id", object.getUserid());
        if(member == null){
            System.out.println("无此会员");
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
        return super.create(object);
    }
}
