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
import org.json.JSONObject;
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
        boolean login = false;
        if(password!= null){
            if(member.getPassword().equals(Coder.encryptMD5(phone + password))){
                login = true;
            }
        }else if(devicesId!=null&&member.getDevicesId().equals(devicesId)&&(c.getTime().getTime()-member.getLastLoginTime().getTime())>0){
            login = true;
        }

        if(login){
            member.setLastLoginTime(loginTime);
            respData.setCode(DefinedConstant.RESPONSE_CODE_SUCCESS);
            MemberLoginResp memberLoginResp = new MemberLoginResp();
            memberLoginResp.setName(member.getName());
            memberLoginResp.setHomeUrl("url");
            memberLoginResp.setInterestCount(member.getAccount());
            memberLoginResp.setPhone(phone);
            memberLoginResp.setUserLevel(member.getLevel());
            memberLoginResp.setUserImage(member.getAvater());
            memberLoginResp.setToken(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()));
            memberService.save(member);
        }else {
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
        }
        return new Gson().toJson(respData);
    }

    /**
     * 会员注册
     * @param name
     * @param phone
     * @param password
     */
    @RequestMapping("/register")
    @ResponseBody
    public String memberRegister(String name,String phone,String password,String devicesId) throws Exception {
        RespData respData = new RespData();
        Member member = memberService.findUniqueBy("phone", phone);
        if(member!=null){
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
            respData.setData("该会员已经存在");
            return new Gson().toJson(respData);
        }
        Member newMember = new Member();
        newMember.setName(name);
        newMember.setPassword(Coder.encryptMD5(phone + password));
        newMember.setDevicesId(devicesId);
        newMember.setLastLoginTime(new Date());
        memberService.save(newMember);

        respData.setCode(DefinedConstant.RESPONSE_CODE_SUCCESS);
        MemberLoginResp memberLoginResp = new MemberLoginResp();
        memberLoginResp.setName(newMember.getName());
        memberLoginResp.setHomeUrl("url");
        memberLoginResp.setInterestCount(newMember.getAccount());
        memberLoginResp.setPhone(newMember.getPhone());
        memberLoginResp.setUserLevel(newMember.getLevel());
        memberLoginResp.setUserImage(newMember.getAvater());
        memberLoginResp.setToken(Coder.encryptMD5(newMember.getPhone() + newMember.getLastLoginTime()));
        respData.setData(memberLoginResp);

        return new Gson().toJson(respData);
    }

    /**
     * 更改密码
     * @param phone
     * @param newPassword
     * @return
     */
    @RequestMapping("/editMemberPas")
    @ResponseBody
    public boolean editMemberPas(String phone,String newPassword,String token) throws Exception {
        Member member = memberService.findUniqueBy("phone", phone);
        if(member==null){
            return false;
        }
        if(token.equals(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()))){
            member.setPassword(Coder.encryptMD5(member.getPhone() + newPassword));
            memberService.save(member);
            return true;
        }
        return false;
    }

    /**
     * 查找账单
     * @param token
     */
    @RequestMapping("/findComsumeLog")
    @ResponseBody
    public String findComsumeLog(String phone,String token){
        RespData respData = new RespData();
        Member member = memberService.findUniqueBy("phone", phone);
        if(member==null){
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
            respData.setData("没有此会员");
            return new Gson().toJson(respData);
        }

        JSONObject jall = new JSONObject();
        jall.put("data", "Hello Spring!");


        return jall.toString();
    }

    /**
     * 修改手机号
     * @param oldphone
     * @param newphone
     * @return
     */
    @RequestMapping("/editPhone")
    @ResponseBody
    public Boolean editPhone(String oldphone,String newphone,String token) throws Exception {
        Member member = memberService.findUniqueBy("phone", oldphone);
        if(member==null){
            return false;
        }
        if(token.equals(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()))){
            member.setPhone(newphone);
            member.setPassword(Coder.encryptMD5(member.getPhone() + member.getPassword()));
            memberService.save(member);
            return true;
        }
        return false;
    }

    @RequestMapping("/editAvater")
    @ResponseBody
    public String editAvater(@RequestParam(value = "file", required = false) MultipartFile file,String phone,String token) throws IOException {
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(localFileUrl+"phone"));
        return  null;
    }


}
