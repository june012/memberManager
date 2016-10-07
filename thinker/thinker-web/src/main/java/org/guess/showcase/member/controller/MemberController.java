package org.guess.showcase.member.controller;

import com.google.gson.Gson;
import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.member.dto.MemberDto;
import org.guess.showcase.member.dto.StoreMemberDto;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.Store;
import org.guess.sys.model.User;
import org.guess.sys.service.StoreService;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
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

    @ResponseBody
    @RequestMapping("/getStoreAndMember")
    public String getStoreAndMember() throws Exception {
        User currentUser = UserUtil.getCurrentUser();
        List<StoreMemberDto> storeMemberDtos = new ArrayList<StoreMemberDto>();
        if(currentUser.getStoreId() == 0){
            List<Store> stores = storeService.getAll();
            for(Store store :stores){
                StoreMemberDto storeMemberDto = new StoreMemberDto();
                storeMemberDto.setId(store.getId());
                storeMemberDto.setName(store.getStoreName());
                List<Member> members = memberService.findBy("storeId", store.getId());
                List<MemberDto> memberDtos = new ArrayList<MemberDto>();
                for(Member m:members){
                    MemberDto memberDto = new MemberDto();
                    BeanUtils.copyProperties(m,memberDto);
                    memberDtos.add(memberDto);
                }
                storeMemberDto.setMemberDtos(memberDtos);
                storeMemberDtos.add(storeMemberDto);
            }
            Gson gson = new Gson();
            return gson.toJson(storeMemberDtos);
        }else{
            StoreMemberDto storeMemberDto = new StoreMemberDto();
            storeMemberDto.setId(currentUser.getStoreId());
            Store store = storeService.findUniqueBy("id", currentUser.getStoreId());
            storeMemberDto.setName(store.getStoreName());
            System.out.println(store.getStoreName());

            List<Member> members = memberService.findBy("storeId", currentUser.getStoreId());
            List<MemberDto> memberDtos = new ArrayList<MemberDto>();
            for(Member m:members){
                MemberDto memberDto = new MemberDto();
                BeanUtils.copyProperties(m,memberDto);
                memberDtos.add(memberDto);
            }
            storeMemberDto.setMemberDtos(memberDtos);
            storeMemberDtos.add(storeMemberDto);
            Gson gson = new Gson();
            return gson.toJson(storeMemberDtos);
        }
    }
}
