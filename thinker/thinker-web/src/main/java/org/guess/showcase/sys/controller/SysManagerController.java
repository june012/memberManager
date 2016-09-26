package org.guess.showcase.sys.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.sys.model.Store;
import org.guess.showcase.sys.service.StoreService;
import org.guess.sys.model.Role;
import org.guess.sys.model.User;
import org.guess.sys.service.RoleService;
import org.guess.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by wan.peng on 2016/9/22.
 */
@Controller
@RequestMapping("/sysManager/admin/")
public class SysManagerController extends BaseController<User> {
    {
        editView = "/sysManager/admin/edit";
        listView = "/sysManager/admin/list";
        showView = "/sysManager/admin/show";
    }


    @Autowired
    private RoleService roleService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Override
    public ModelAndView create() throws Exception {
        ModelAndView mav = new ModelAndView(editView);
        List<Role> roles = roleService.getAll();
        mav.addObject("roles", roles);
        List<Store> stores = storeService.getAll();
        mav.addObject("stores", stores);
        return mav;
    }

    @Override
    public String create(User user) throws Exception {

        String oldpwd = request.getParameter("oldpwd");
        String roleId = request.getParameter("roleId");
        String storeId = request.getParameter("storeId");
        userService.save(user,roleId,oldpwd,storeId);
        return REDIRECT + listView;
    }

    @Override
    public ModelAndView update(@PathVariable("id") Long id) throws Exception {
        ModelAndView mav = super.update(id);
        List<Role> roles = roleService.getAll();
        mav.addObject("roles", roles);
        List<Store> stores = storeService.getAll();
        mav.addObject("stores", stores);
        return mav;
    }
}
