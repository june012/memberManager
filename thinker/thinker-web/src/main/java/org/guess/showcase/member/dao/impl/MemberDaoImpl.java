package org.guess.showcase.member.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.member.dao.MemberDao;
import org.guess.showcase.member.model.Member;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class MemberDaoImpl extends HibernateDao<Member,Long> implements MemberDao{
}
