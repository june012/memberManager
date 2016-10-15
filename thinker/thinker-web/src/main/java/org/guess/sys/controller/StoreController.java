package org.guess.sys.controller;

import com.google.gson.Gson;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        String storeName = request.getParameter("search_LIKES_storeName");
        String id = request.getParameter("search_EQL_id");
        String hql ="from Store store where";
        if (currentUser.getStoreId()==0){
            hql+=" 1=1 ";
        }else{
            hql+=" store.id ="+currentUser.getStoreId();
        }
        if(id!=null){
            hql+=" and store.id="+id;
        }
        if(storeName!=null){
            hql+=" and store.storeName like '%"+storeName+"%'";

        }
        Page<Store> page1 = storeService.findPage(page, hql);
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

    @ResponseBody
    @RequestMapping("/getStores")
    public String getStores(){
        User currentUser = UserUtil.getCurrentUser();
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
        Gson gson = new Gson();
        return gson.toJson(stores);
    }

}
