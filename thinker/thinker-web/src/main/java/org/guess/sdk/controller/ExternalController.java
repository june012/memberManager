package org.guess.sdk.controller;

import com.google.gson.Gson;
import org.guess.core.utils.FileUtils;
import org.guess.core.utils.security.Coder;
import org.guess.facility.CalendarUtils;
import org.guess.facility.DefinedConstant;
import org.guess.sdk.dto.ConsumeInfo;
import org.guess.sdk.dto.ConsumeRespData;
import org.guess.sdk.dto.MemberLoginResp;
import org.guess.sdk.dto.RespData;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.service.ConsumeLogService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wan.peng on 2016/10/11.
 */
@Controller
@RequestMapping("/remote/")
public class ExternalController{

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumeLogService consumeLogService;



    private final String localFileUrl="";



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
    public RespData memberRegister(String name,String phone,String password,String devicesId) throws Exception {
        RespData respData = new RespData();
        Member member = memberService.findUniqueBy("phone", phone);
        if(member!=null){
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
            respData.setData("该会员已经存在");
            return respData;
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

        return respData;
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
    public List<ConsumeRespData> findComsumeLog(String phone,String token){
        List<ConsumeRespData> consumeRespDatas = new ArrayList<ConsumeRespData>();
        RespData respData = new RespData();
        Member member = memberService.findUniqueBy("phone", phone);
        if(member==null){
            return consumeRespDatas;
        }
        if(!token.equals(Coder.encryptMD5(member.getPhone() + member.getLastLoginTime()))){
            return consumeRespDatas;
        }
        List<ConsumeLog> consumeLogs = consumeLogService.findBy("memberId", member.getId());
        Map<String,List<ConsumeInfo>> stringListMap = new HashMap<String, List<ConsumeInfo>>();
        DateFormat dateFormat = new SimpleDateFormat("yy-MM");

        for(ConsumeLog consumeLog : consumeLogs){
            Date createTime = consumeLog.getCreateTime();

            String format = dateFormat.format(createTime);
            ConsumeInfo consumeInfo = new ConsumeInfo();
            consumeInfo.setWeek(CalendarUtils.getWeek(createTime));
            consumeInfo.setBillAmount(consumeLog.getAccount());
            consumeInfo.setBillId(String.valueOf(consumeLog.getId()));

            if(stringListMap.get(format)==null){
                List<ConsumeInfo> consumeInfos = new ArrayList<ConsumeInfo>();
                consumeInfos.add(consumeInfo);
                stringListMap.put(format,consumeInfos);
            }else{
                stringListMap.get(format).add(consumeInfo);
            }
        }
        Set<String> strings = stringListMap.keySet();
        for(String time:strings){
            ConsumeRespData consumeRespData = new ConsumeRespData();
            consumeRespData.setMonth(time);
            consumeRespData.setItemList(stringListMap.get(time));
            consumeRespDatas.add(consumeRespData);
        }

        return consumeRespDatas;
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
    public RespData editAvater(@RequestParam(value = "file", required = false) MultipartFile file, String phone, String token, HttpServletRequest request) throws IOException {
        RespData respData = new RespData();
        Member member = memberService.findUniqueBy("phone", phone);
        if(member==null){
            respData.setCode(DefinedConstant.RESPONSE_CODE_ERROR);
            respData.setData("没有此会员");
            return respData;
        }
        String realPath = request.getSession().getServletContext().getRealPath("\\assets\\img/temp\\");
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(realPath+file.getOriginalFilename()));
        member.setAvater(localFileUrl+realPath);
        try {
            memberService.save(member);
            respData.setCode(DefinedConstant.RESPONSE_CODE_SUCCESS);
            JSONObject jall = new JSONObject();
            jall.put("userImage", member.getAvater());
            respData.setData(jall.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  respData;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        System.out.println("11111111111111111");

        return "113242";
    }


}
