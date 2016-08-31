package org.guess.sdk.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by wan.peng on 2016/8/31.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HelloWorldService {
    public String getNewName(String userName);
}
