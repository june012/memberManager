package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.ConsumeLogDao;
import org.guess.showcase.consume.model.ConsumeLog;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Repository
public class ConsumeLogDaoImpl extends HibernateDao<ConsumeLog,Long> implements ConsumeLogDao {
}
