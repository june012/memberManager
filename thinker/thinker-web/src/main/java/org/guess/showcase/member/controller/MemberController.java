package org.guess.showcase.member.controller;

import com.google.gson.Gson;
import org.guess.core.orm.Page;
import org.guess.core.utils.security.Coder;
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
import java.text.SimpleDateFormat;
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
        String hql = "from Member m where m.status = 'A'";
        String search_eqs_phone = request.getParameter("search_EQS_phone");
        String search_likes_name = request.getParameter("search_LIKES_name");
        if(search_eqs_phone != null){
            hql += " and m.phone = '"+search_eqs_phone+"'";
        }
        if(search_likes_name != null){
            hql += " and m.name like '%"+search_likes_name+"%'";
        }
        if(currentUser.getStoreId() == 0){
            List<Store> stores = null;
            try {
                stores = storeService.getAll();
            } catch (Exception e) {
                logger.error("不存在门店");
                e.printStackTrace();
            }
            Store store = new Store();
            store.setId(Long.valueOf("0"));
            store.setStoreName("总店");
            stores.add(store);
            request.getSession().setAttribute("stores",stores);

            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                hql += " and m.storeId = "+search_eql_storeId;
            }
            Page<Member> page0 = memberService.findPage(page, hql);
            return page0.returnMap();
        }
        List<Store> stores = storeService.findBy("id", currentUser.getStoreId());
        request.getSession().setAttribute("stores",stores);
        hql +=" and m.storeId=" + currentUser.getStoreId();
        Page<Member> page1 = memberService.findPage(page,hql);
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

    @RequestMapping("/create")
    public String create(@Valid Member object,HttpServletRequest request) throws Exception {

        System.out.println(object.getAvater());
        List<Member> members = memberService.findBy("phone", object.getPhone());
        if(members.size()>1){
            logger.info("无法识别"+object.getPhone());
            return null;
        }
        if(object.getLastLoginTime() == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            object.setLastLoginTime(simpleDateFormat.parse("1999-01-01"));
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
        if(object.getInterest()==null){
            object.setInterest(new BigDecimal("0"));
        }
        if(object.getCanBeConsumed()==null){
            object.setCanBeConsumed(new BigDecimal("0"));
        }
        if(object.getCredit()==null){
            object.setCredit(Long.valueOf("0"));
        }
        if(object.getStatus()==null){
            object.setStatus("A");
        }
        if(object.getAvater()==null||object.getAvater()==""){
            object.setAvater("/assets/img/avatar.png");
        }else{
            System.out.println("上传图片");
        }
        if (object.getId() != 0) {
            String oldpwd = request.getParameter("oldpwd");
            if (!oldpwd.equals(object.getPassword())) {
                object.setPassword(Coder.encryptMD5(object.getPhone() + object.getPassword()));
            }
        } else {
            object.setPassword(Coder.encryptMD5(object.getPhone() + object.getPassword()));
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
            Store store1 = new Store();
            store1.setId(Long.valueOf("0"));
            stores.add(store1);
            long id = 1;
            for(Store store :stores){
                StoreMemberDto storeMemberDto = new StoreMemberDto();
                storeMemberDto.setId(id++);
                storeMemberDto.setStoreId(store.getId());
                if(store.getId()==0){
                    storeMemberDto.setName("总店");
                }else{
                    storeMemberDto.setName(store.getStoreName());
                }
                List<Member> members = memberService.findMembers("EQL_storeId", store.getId().toString());
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

            List<Member> members = memberService.findMembers("EQL_storeId", store.getId().toString());
            List<MemberDto> memberDtos = new ArrayList<MemberDto>();
            for(Member m:members){
                MemberDto memberDto = new MemberDto();
                BeanUtils.copyProperties(m,memberDto);
                memberDtos.add(memberDto);
            }
            storeMemberDto.setMemberDtos(memberDtos);
            storeMemberDtos.add(storeMemberDto);
            Gson gson = new Gson();
            System.out.println("--"+gson.toJson(storeMemberDtos));
            return gson.toJson(storeMemberDtos);
        }
    }

    @ResponseBody
    @RequestMapping("/queryMemberByPhone")
    public String queryMemberByPhone(@RequestParam("phone") String phone){
        System.out.println(phone);
        Gson gson = new Gson();
        List<Member> members;
        User currentUser = UserUtil.getCurrentUser();
        if(currentUser.getStoreId() == 0){
            members = memberService.findMembers("EQS_phone",phone);
        }else{
            members = memberService.findMembers("EQS_phone",phone,"EQL_storeId", currentUser.getStoreId().toString());
        }
        System.out.println(gson.toJson(members));
        if(members.size()>1){
            logger.info("用户信息错误 出现一个以上相同手机号会员");
        }
        List<StoreMemberDto> storeMemberDtos = new ArrayList<StoreMemberDto>();
        if(members.size()>1||members == null||members.size()==0){
            return gson.toJson(storeMemberDtos);
        }
        Member member = members.get(0);
        StoreMemberDto storeMemberDto = new StoreMemberDto();
        storeMemberDto.setId(Long.valueOf("1"));
        List<MemberDto> memberDtos = new ArrayList<MemberDto>();
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member,memberDto);
        memberDtos.add(memberDto);
        storeMemberDto.setMemberDtos(memberDtos);
        return gson.toJson(storeMemberDto);
    }
}
