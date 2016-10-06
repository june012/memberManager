package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.FillRecord;
import org.guess.showcase.consume.service.FillService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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

    /**
     * 提现周期
     */
    private final int circle= 2;


    @Override
    public String create(@Valid FillRecord object) throws Exception {
        BigDecimal money = object.getMoney();
        Member member = memberService.findUniqueBy("id", object.getUserid());
        if(member == null){
            System.out.println("无此会员");
        }
        if(object.getId() == 0){
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
        return super.create(object);
    }
}
