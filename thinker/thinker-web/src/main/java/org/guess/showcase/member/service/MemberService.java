package org.guess.showcase.member.service;

import org.guess.core.service.BaseService;
import org.guess.showcase.member.model.Member;

import java.util.List;

/**
 * Created by wan.peng on 2016/9/27.
 */
public interface MemberService extends BaseService<Member,Long> {

    void deleteMember(Long id) throws Exception;

    List<Member> findMembers(String property,String value);

    List<Member> findMembers(String property1,String value1,String property2,String value2);

    String findMemberIds(String storeId);
}
