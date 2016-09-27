package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.FillDao;
import org.guess.showcase.consume.model.FillRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class FillDaoImpl extends HibernateDao<FillRecord,Long> implements FillDao {
}
