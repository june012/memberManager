package org.guess.sdk.service.impl;

import org.guess.sdk.service.HelloWorldService;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by wan.peng on 2016/8/31.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SuppressWarnings("deprecation")
public class HelloWorldServiceImpl implements HelloWorldService{

    public String getNewName(String userName) {
        System.out.println("22222222222222222");
        return "Hello Spring!" + userName;
    }
}
