package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.InterestDao;
import org.guess.showcase.consume.model.InterestRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class InterestDaoImpl extends HibernateDao<InterestRecord,Long> implements InterestDao {
}
