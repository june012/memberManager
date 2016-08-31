package org.guess.modules.test.service.impl;

import org.apache.cxf.feature.Features;
import org.guess.modules.test.entity.MybatiesTest;
import org.guess.modules.test.mapper.MyBatiesTestDao;
import org.guess.modules.test.service.MyBatiesTestService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by garyr on 2015/4/18.
 */
// serviceName指明WSDL中<wsdl:service>与<wsdl:binding>元素的名称, endpointInterface属性指向Interface类全称.
@WebService(serviceName = "AccountService", endpointInterface = "org.guess.modules.test.service.MyBatiesTestService", targetNamespace = "htp://test.cxf.guess.com")
// 增加inbound/outbound SOAP内容的日志
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class MyBatiesTestServiceImpl implements MyBatiesTestService{

    @Autowired
    private MyBatiesTestDao dao;

    public List<MybatiesTest> list() {
//        return dao.list("test","1");
        return null;
    }
}
