package org.guess.sys.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.sys.model.Role;
import org.guess.sys.model.Store;
import org.guess.sys.model.User;
import org.guess.sys.service.RoleService;
import org.guess.sys.service.StoreService;
import org.guess.sys.service.UserService;
import org.guess.sys.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/22.
 */
@Controller
@RequestMapping("/sys/admin/")
public class SysManagerController extends BaseController<User> {
    {
        editView = "/sys/admin/edit";
        listView = "/sys/admin/list";
        showView = "/sys/admin/show";
    }

    private String ADMIN_ID = "11";


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
        User currentUser = UserUtil.getCurrentUser();
        mav.addObject("currentUser", currentUser);
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
        User currentUser = UserUtil.getCurrentUser();
        mav.addObject("currentUser", currentUser);
        return mav;
    }

    @RequestMapping("isAvailable")
    public @ResponseBody
    boolean isLoginIdAvailable(@RequestParam("loginId") String id, @RequestParam("oldValue") String old) {
        if (id.equals(old))
            return true;
        User u = userService.findUniqueBy("loginId", id);
        return u == null;
    }

    /**
     * 获取所有用户
     * @throws Exception
     */
    @RequestMapping(value="getAllUsers")
    @ResponseBody
    public List<User> getAllUsers() throws Exception{
        return userService.getAll();
    }

    @Override
    public Map<String, Object> page(Page<User> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        boolean id = currentUser.getRoles().contains(roleService.findUniqueBy("id", Long.valueOf(ADMIN_ID)));
        if(id){
            return super.page(page, request);
        }
        Page<User> page1 = userService.findPage(page, "from User user where user.storeId = " + currentUser.getStoreId());
        return page1.returnMap();
    }

}
