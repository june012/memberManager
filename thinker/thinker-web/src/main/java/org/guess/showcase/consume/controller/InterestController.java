package org.guess.showcase.consume.controller;

import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.InterestRecord;
import org.guess.showcase.consume.service.InterestService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.User;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/interest")
public class InterestController extends BaseController<InterestRecord>{
    {
        editView = "/consume/interest/edit";
        listView = "/consume/interest/list";
        showView = "/consume/interest/show";
    }

    @Autowired
    private InterestService interestService;

    @Autowired
    private MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(InterestController.class);


    @Override
    public Map<String, Object> page(Page<InterestRecord> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from InterestRecord a where 1=1 ";
        String search_EQL_userId = request.getParameter("search_EQL_userId");
        String search_EQD_date = request.getParameter("search_EQD_date");
        String search_EQS_phone = request.getParameter("search_EQS_phone");

        if(search_EQL_userId!= null){
            hql+= " and a.userId="+search_EQL_userId;
        }

        if(search_EQD_date!= null){
            hql+= " and date_format(a.date,'%Y-%c-%d')='"+search_EQD_date+"'";
        }
        if(search_EQS_phone != null){
            Member phone = memberService.findUniqueBy("phone", search_EQS_phone);
            if(phone!=null){
                hql+=" and a.userId="+phone.getId();
            }else {
                hql+=" and 1=2";
            }
        }
        if(currentUser.getStoreId() == 0){
            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                String memberIds = memberService.findMemberIds(search_eql_storeId);
                if (memberIds!=null){
                    hql+=" and a.userId in("+memberIds+")";
                }
            }
        }
        return interestService.findPage(page,hql).returnMap();
    }


    public String updateRate(double rate){

        return null;
    }

}
