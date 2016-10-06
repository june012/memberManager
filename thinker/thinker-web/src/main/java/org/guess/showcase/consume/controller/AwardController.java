package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.AwardRecord;
import org.guess.showcase.consume.service.AwardService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
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

    @Override
    public String create(@Valid AwardRecord object) throws Exception {
        BigDecimal awardMoney = object.getAwardMoney();
        Member member = memberService.findUniqueBy("id", object.getMemberId());
        if(member == null){
            System.out.println("无此会员");
        }
        if(object.getId() == 0){
            member.setAward(member.getAward().add(awardMoney));
            object.setDate(new Date());
        }else{
            AwardRecord awardRecord = awardService.findUniqueBy("id", object.getId());
            member.setAward(member.getAward().subtract(awardRecord.getAwardMoney()).add(awardMoney));
            object.setDate(awardRecord.getDate());
        }
        memberService.save(member);
        object.setAwardAfter(member.getAward());
        return super.create(object);
    }


}
