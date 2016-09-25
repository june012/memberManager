package org.guess.showcase.sys.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.sys.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/22.
 */
@Controller
@RequestMapping("/sysManager/admin/")
public class SysManagerController extends BaseController<User> {
    {
        editView = "/sysManager/edit";
        listView = "/sysManager/list";
        showView = "/sysManager/show";
    }



    @Override
    public Map<String, Object> page(Page<User> page, HttpServletRequest request) {


        return super.page(page, request);
    }
}
