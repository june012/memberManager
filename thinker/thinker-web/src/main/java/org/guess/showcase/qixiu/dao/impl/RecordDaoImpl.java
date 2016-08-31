package org.guess.showcase.qixiu.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.core.orm.hibernate.NativeSqlQueryHibernateDao;
import org.guess.showcase.qixiu.dao.RecordDao;
import org.guess.showcase.qixiu.model.Record;
import org.springframework.stereotype.Repository;

/**
 * 记录daoImpl
 * @author rguess
 * @version 2014-06-04
 */
@Repository
public class RecordDaoImpl extends NativeSqlQueryHibernateDao<Record,Long> implements RecordDao {

}
