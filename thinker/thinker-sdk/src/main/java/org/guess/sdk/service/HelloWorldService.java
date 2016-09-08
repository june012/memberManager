package org.guess.sdk.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by wan.peng on 2016/8/31.
 */
//@WebService
//@SOAPBinding(style = SOAPBinding.Style.RPC)

@Path("/userService")
public interface HelloWorldService {
    @GET
    @Path("getName")
    @Produces(MediaType.APPLICATION_XML)
    String getNewName(@QueryParam("userName")String userName);
}
