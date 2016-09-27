package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.AwardDao;
import org.guess.showcase.consume.model.AwardRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class AwardDaoImpl extends HibernateDao<AwardRecord,Long> implements AwardDao {
}
