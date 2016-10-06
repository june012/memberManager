package org.guess.showcase.member.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.Store;
import org.guess.sys.model.User;
import org.guess.sys.service.StoreService;
import org.guess.sys.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/member/")
public class MemberController extends BaseController<Member> {
    {
        editView = "/member/edit";
        listView = "/member/list";
        showView = "/member/show";
    }

    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreService storeService;

    @Override
    public Map<String, Object> page(Page<Member> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        if(currentUser.getStoreId() == 0){
            return super.page(page, request);
        }
        Page<Member> page1 = memberService.findPage(page,"from Member member where member.storeId=" + currentUser.getStoreId());
        return page1.returnMap();
    }

    @Override
    public ModelAndView create() throws Exception {
        ModelAndView modelAndView = super.create();
        List<Store> stores = storeService.getAll();
        modelAndView.addObject("stores",stores);
        User currentUser = UserUtil.getCurrentUser();
        modelAndView.addObject("currentUser",currentUser);
        return modelAndView;
    }

    @Override
    public ModelAndView update(@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = super.update(id);
        List<Store> stores = storeService.getAll();
        modelAndView.addObject("stores",stores);
        User currentUser = UserUtil.getCurrentUser();
        modelAndView.addObject("currentUser",currentUser);
        return modelAndView;
    }

    @Override
    public String create(@Valid Member object) throws Exception {
        if(object.getAccount() == null){
            object.setAccount(new BigDecimal("0"));
        }
        if(object.getAward() == null){
            object.setAward(new BigDecimal("0"));
        }
        if(object.getPrincipal()==null){
            object.setPrincipal(new BigDecimal("0"));
        }

        return super.create(object);
    }

    @RequestMapping("isAvailable")
    public @ResponseBody
    boolean isLoginIdAvailable(@RequestParam("oldValue") String old) {
        Member member = memberService.findUniqueBy("phone", old);

        System.out.println("-----------------"+old+"---------------");
        return member == null;
    }
}
