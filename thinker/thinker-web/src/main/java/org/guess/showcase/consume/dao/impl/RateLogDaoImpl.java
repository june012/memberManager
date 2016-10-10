package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.RateLogDao;
import org.guess.showcase.consume.model.RateLog;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Repository
public class RateLogDaoImpl extends HibernateDao<RateLog,Long> implements RateLogDao {
}
