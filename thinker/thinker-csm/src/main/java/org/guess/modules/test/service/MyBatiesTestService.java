package org.guess.modules.test.service;

import org.guess.modules.test.entity.MybatiesTest;

import javax.jws.WebService;
import java.util.List;

/**
 * @author garyr
 */
@WebService(name = "MyBatiesTestService", targetNamespace = "htp://test.cxf.guess.com")
public interface MyBatiesTestService {

    List<MybatiesTest> list();
}
