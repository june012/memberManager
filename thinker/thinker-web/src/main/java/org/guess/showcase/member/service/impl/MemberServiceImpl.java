package org.guess.showcase.member.service.impl;

import org.guess.core.Constants;
import org.guess.core.orm.PropertyFilter;
import org.guess.core.service.BaseServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService{


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
    public void deleteMember(Long id) throws Exception {
        Member member = findUniqueBy("id", id);
        member.setStatus(Constants.MEMBER_STATUS_DELETE);
        save(member);
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
    }

    @Override
    public List<Member> findMembers(String property, String value) {
        List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
        PropertyFilter propertyFilter1 = new PropertyFilter("EQS_status", "A");
        PropertyFilter propertyFilter2 = new PropertyFilter(property, value);
        propertyFilters.add(propertyFilter1);
        propertyFilters.add(propertyFilter2);
        return find(propertyFilters);
    }

    @Override
    public List<Member> findMembers(String property1, String value1, String property2, String value2) {
        List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
        PropertyFilter propertyFilter1 = new PropertyFilter("EQS_status", "A");
        PropertyFilter propertyFilter2 = new PropertyFilter(property1, value1);
        PropertyFilter propertyFilter3 = new PropertyFilter(property2, value2);
        propertyFilters.add(propertyFilter1);
        if(value1 !=null){
            propertyFilters.add(propertyFilter2);
        }if(value2!= null){
            propertyFilters.add(propertyFilter3);
        }
        return find(propertyFilters);
    }

    @Override
    public String findMemberIds(String storeId) {
        List<Member> members = findBy("storeId", Long.valueOf(storeId));
        String memberIds="";
        for(Member m :members){
            memberIds+=m.getId()+",";
        }
        if(members.size()>0) {
            String substring = memberIds.substring(0, memberIds.length() - 1);
            return substring;
        }else{
            return null;
        }

    }
}
