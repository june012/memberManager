package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.CashDao;
import org.guess.showcase.consume.model.CashRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class CashDaoImpl extends HibernateDao<CashRecord,Long> implements CashDao {
}
