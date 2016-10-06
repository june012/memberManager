package org.guess.showcase.member.controller;

import org.guess.core.Constants;
import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.AwardRecord;
import org.guess.showcase.consume.model.CashRecord;
import org.guess.showcase.consume.model.DrawRecord;
import org.guess.showcase.consume.model.FillRecord;
import org.guess.showcase.consume.model.InterestRecord;
import org.guess.showcase.consume.service.AwardService;
import org.guess.showcase.consume.service.CashService;
import org.guess.showcase.consume.service.DrawService;
import org.guess.showcase.consume.service.FillService;
import org.guess.showcase.consume.service.InterestService;
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

    @Autowired
    private AwardService awardService;

    @Autowired
    private CashService cashService;

    @Autowired
    private FillService fillService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private DrawService drawService;

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
        Member member = memberService.findUniqueBy("id", id);
        member.setStatus(Constants.MEMBER_STATUS_DELETE);
        memberService.save(member);

        List<AwardRecord> awardRecords = awardService.findBy("memberId", id);
        if(awardRecords!=null&&awardRecords.size()!=0){
            Long[] ids = new Long[awardRecords.size()];
            for(int i=0;i<awardRecords.size();i++){
                ids[i]=awardRecords.get(i).getId();
            }
            awardService.removeByIds(ids);
        }

        List<CashRecord> cashRecords = cashService.findBy("userid", id);
        if(cashRecords!=null&&cashRecords.size()!=0) {
            Long[] ids1 = new Long[cashRecords.size()];
            System.out.println(ids1.length + "-" + cashRecords.size());
            for (int i = 0; i < cashRecords.size(); i++) {
                ids1[i] = cashRecords.get(i).getId();
            }
            cashService.removeByIds(ids1);
        }

        List<FillRecord> fillRecords = fillService.findBy("userid", id);
        if(fillRecords!=null&&fillRecords.size()!=0) {
            Long[] ids2 = new Long[fillRecords.size()];
            for (int i = 0; i < fillRecords.size(); i++) {
                ids2[i] = fillRecords.get(i).getId();
            }
            fillService.removeByIds(ids2);
        }

        List<InterestRecord> interestRecords = interestService.findBy("userId", id);
        if(interestRecords!=null&&interestRecords.size()!=0) {
            Long[] ids3 = new Long[interestRecords.size()];
            for(int i=0;i<interestRecords.size();i++){
                ids3[i]=interestRecords.get(i).getId();
            }
            interestService.removeByIds(ids3);
        }


        List<DrawRecord> drawRecords = drawService.findBy("userid", id);
        if(drawRecords!=null&&drawRecords.size()!=0) {
            Long[] ids4 = new Long[drawRecords.size()];
            for (int i = 0; i < drawRecords.size(); i++) {
                ids4[i] = drawRecords.get(i).getId();
            }
            drawService.removeByIds(ids4);
        }

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
