package org.guess.sdk;

import org.guess.showcase.consume.service.CashService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * Created by wan.peng on 2016/10/11.
 */
@Controller
@RequestMapping("/services")
public class ExternalController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CashService cashService;



    private static final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    /**
     * 会员登录
     * @param phone
     * @param password
     */
    @RequestMapping("/login")
    public void memberLogin(String phone,String password){
        Member member = memberService.findUniqueBy("phone", phone);

    }

    /**
     * 会员注册
     * @param name
     * @param phone
     * @param password
     */
    @RequestMapping("/register")
    public void memberRegister(String name,String phone,String password){

    }

    /**
     * 更改密码
     * @param phone
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/editMemberPas")
    public boolean editMemberPas(String phone,String oldPassword,String newPassword){

        return false;
    }

    /**
     * 查找账单
     * @param token
     */
    @RequestMapping("/findComsumeLog")
    public void findComsumeLog(String token){

    }

    /**
     * 查找消费记录
     * @param token
     */
    @RequestMapping("/findComsumeRecord")
    public void findComsumeRecord(String token){

    }

    /**
     * 修改手机号
     * @param oldphone
     * @param newphone
     * @return
     */
    @RequestMapping("/editPhone")
    public Boolean editPhone(String oldphone,String newphone){

        return false;
    }

    @RequestMapping("/editAvater")
    public String editAvater(File avater){

        return  null;
    }


}
