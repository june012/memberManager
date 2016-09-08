package org.guess.sdk.service.impl;

import org.guess.sdk.service.HelloWorldService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by wan.peng on 2016/8/31.
 */
//@WebService
//@SOAPBinding(style = SOAPBinding.Style.RPC)
@Service("userService")
public class HelloWorldServiceImpl implements HelloWorldService{

    public String getNewName(String userName) {
        System.out.println("22222222222222222"+userName);
        JSONObject jall = new JSONObject();
        jall.put("data", "Hello Spring!"+userName);//Map转换成Json
        return jall.toString();
    }
}
