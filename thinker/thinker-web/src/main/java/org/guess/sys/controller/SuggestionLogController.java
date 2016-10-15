package org.guess.sys.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.SuggestionLog;
import org.guess.sys.model.User;
import org.guess.sys.service.SuggestionLogService;
import org.guess.sys.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Controller
@RequestMapping("/sys/suggestion/")
public class SuggestionLogController extends BaseController<SuggestionLog> {
    {
        editView = "/sys/suggestion/edit";
        listView = "/sys/suggestion/list";
        showView = "/sys/suggestion/show";
    }

    @Autowired
    private SuggestionLogService suggestionLogService;

    @Autowired
    private MemberService memberService;

    @Override
    public Map<String, Object> page(Page<SuggestionLog> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from SuggestionLog a where 1=1 ";
        if(currentUser.getStoreId() == 0){
            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                List<Member> members = memberService.findBy("storeId", Long.valueOf(search_eql_storeId));
                String memberIds="";
                for(Member m :members){
                    memberIds+=m.getId()+",";
                }
                String substring = "";
                if(members.size()>0){
                    substring = memberIds.substring(0, memberIds.length() - 1);
                    System.out.println(substring);
                    hql+=" and a.memberId in("+substring+")";
                }else{
                    hql+=" and 1=2";
                }
            }
        }
        return suggestionLogService.findPage(page,hql).returnMap();
    }
}
