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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        User currentUser = UserUtil.getCurrentUser();
        List<Role> roles = roleService.getAll();
        mav.addObject("roles", roles);
        List<Store> stores = null;
        if(currentUser.getStoreId() == 0){
            Store store0 = new Store();
            store0.setId(Long.valueOf("0"));
            store0.setStoreName("总店");
            try {
                stores = storeService.getAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stores.add(store0);
        }else{
            stores= new ArrayList<Store>();
            Store store = storeService.findUniqueBy("id", currentUser.getStoreId());
            stores.add(store);
        }
        mav.addObject("stores", stores);
        mav.addObject("currentUser", currentUser);
        return mav;
    }

    /**
     * 修改个人信息
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/updateMyself")
    public ModelAndView updateMyself() throws Exception {
        User currentUser = UserUtil.getCurrentUser();
        ModelAndView mav = super.update(currentUser.getId());
        List<Role> roles = roleService.getAll();
        mav.addObject("roles", roles);
        List<Store> stores = null;
        if(currentUser.getStoreId() == 0){
            Store store0 = new Store();
            store0.setId(Long.valueOf("0"));
            store0.setStoreName("总店");
            try {
                stores = storeService.getAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stores.add(store0);
        }else{
            stores= new ArrayList<Store>();
            Store store = storeService.findUniqueBy("id", currentUser.getStoreId());
            stores.add(store);
        }
        mav.addObject("stores", stores);
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
        request.getSession().setAttribute("currentUser", currentUser);
        String name = request.getParameter("search_LIKES_name");
        String storeId = request.getParameter("search_LIKES_storeId");
        String hql = "from User user where ";
        if(currentUser.getStoreId()==0){
            hql+="1=1 ";
        }else{
            hql +="user.storeId =" + currentUser.getStoreId();
        }
        if(name != null){
            hql+=" and user.name like '%"+name+"%'";
        }
        if(storeId!=null){
            hql+= " and user.storeId ="+storeId;
        }
        Page<User> page1 = userService.findPage(page, hql);
        return page1.returnMap();
    }



}
