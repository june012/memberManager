package org.guess.showcase.member.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.Store;
import org.guess.sys.model.User;
import org.guess.sys.service.StoreService;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Override
    public Map<String, Object> page(Page<Member> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        if(currentUser.getStoreId() == 0){
            Page<Member> page0 = memberService.findPage(page, "from Member m where m.status<>'D'");
            return page0.returnMap();
        }
        Page<Member> page1 = memberService.findPage(page,"from Member m where m.storeId=" + currentUser.getStoreId()+" and m.status='A'");
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
    public String delete(@PathVariable("id") Long id) throws Exception {
        memberService.deleteMember(id);
        return "redirect:/member/list";
    }

    @Override
    public String delete(@RequestParam("ids") Long[] ids, HttpServletRequest request) throws Exception {
        for(int i = 0;i<ids.length;i++){
            delete(ids[i]);
        }
        return "redirect:/member/list";
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
        List<Member> members = memberService.findBy("phone", object.getPhone());
        if(members.size()>0){
            logger.info("该手机号{}已被注册"+object.getPhone());
            return null;
        }
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
    boolean isLoginIdAvailable(@RequestParam("phone") String phone) {
        List<Member> members = memberService.findBy("phone", phone);
        if(members.size()==0||members == null){
            return true;
        }
        return false;
    }
}
