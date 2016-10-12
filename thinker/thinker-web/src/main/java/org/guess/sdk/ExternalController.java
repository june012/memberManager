package org.guess.sdk;

import com.google.gson.Gson;
import org.guess.core.utils.FileUtils;
import org.guess.core.utils.security.Coder;
import org.guess.facility.DefinedConstant;
import org.guess.sdk.dto.MemberLoginResp;
import org.guess.sdk.dto.RespData;
import org.guess.showcase.consume.service.CashService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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

    private final String localFileUrl="thinker/avater/";



    private static final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    /**
     * 会员登录
     * @param phone
     * @param password
     */
    @RequestMapping("/login")
    @ResponseBody
    public String memberLogin(String phone,String password,String devicesId) throws Exception {
        Member member = memberService.findUniqueBy("phone", phone);
        RespData respData = new RespData();
        Date loginTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(loginTime);
        c.add(Calendar.MONTH,1);
        Gson gson = new Gson();
        if (password==null){
            if (devicesId!=null&&member.getDevicesId().equals(devicesId)&&(c.getTime().getTime()-member.getLastLoginTime().getTime())>0){
                member.setLastLoginTime(loginTime);
                respData.setCode(DefinedConstant.RESPONSE_CODE_SUCCESS);
                MemberLoginResp memberLoginResp = new MemberLoginResp();
                memberLoginResp.setHomeUrl("url");
                memberLoginResp.setInterestCount(member.getCredit());
                memberLoginResp.setPhone(phone);
                memberLoginResp.setUserImage(member.getAvater());
                memberLoginResp.setToken(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()));
                memberService.save(member);
            }

        }
        if(member.getPassword().equals(Coder.encryptMD5(phone + password))){
            member.setLastLoginTime(loginTime);
            respData.setCode(DefinedConstant.RESPONSE_CODE_SUCCESS);
            MemberLoginResp memberLoginResp = new MemberLoginResp();
            memberLoginResp.setHomeUrl("url");
            memberLoginResp.setInterestCount(member.getCredit());
            memberLoginResp.setPhone(phone);
            memberLoginResp.setUserImage(member.getAvater());
            memberLoginResp.setToken(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()));
            memberService.save(member);
        }else{
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
        }
        return gson.toJson(respData);
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
    @ResponseBody
    public String editAvater(@RequestParam(value = "file", required = false) MultipartFile file,String phone,String token) throws IOException {
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(localFileUrl+"phone"));
        return  null;
    }


}
