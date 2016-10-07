package org.guess.showcase.member.service;

import org.guess.core.service.BaseService;
import org.guess.showcase.member.model.Member;

/**
 * Created by wan.peng on 2016/9/27.
 */
public interface MemberService extends BaseService<Member,Long>{

    void deleteMember(Long id) throws Exception;
}
