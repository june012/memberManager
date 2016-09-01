package org.guess.sdk.service.impl;

import org.guess.sdk.service.HelloWorldService;
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
        return "Hello Spring!" + userName;
    }
}
