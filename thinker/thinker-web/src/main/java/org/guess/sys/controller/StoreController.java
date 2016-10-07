package org.guess.sys.controller;

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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/25.
 */
@Controller
@RequestMapping("/sys/store/")
public class StoreController extends BaseController<Store>{
    {
        editView = "/sys/store/edit";
        listView = "/sys/store/list";
        showView = "/sys/store/show";
    }
    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberService memberService;


    @Override
    public Map<String, Object> page(Page<Store> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        if(currentUser.getStoreId()==0){
            return super.page(page, request);
        }
        Page<Store> page1 = storeService.findPage(page, "from Store store where store.id="+currentUser.getStoreId());
        return page1.returnMap();
    }

    @Override
    public String delete(@PathVariable("id") Long id) throws Exception {
        super.delete(id);
        List<Member> members = memberService.findBy("storeId", id);
        for(Member member:members){
            memberService.deleteMember(member.getId());
        }
        return "redirect:/sys/store/list";
    }

    @Override
    public String delete(@RequestParam("ids") Long[] ids, HttpServletRequest request) throws Exception {
        for(int i=0;i<ids.length;i++){
            delete(ids[i]);
        }
        return "redirect:/sys/store/list";
    }
}
