package org.guess.showcase.sys.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.sys.dao.StoreDao;
import org.guess.showcase.sys.model.Store;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/25.
 */
@Repository
public class StoreDaoImpl extends HibernateDao<Store,Long> implements StoreDao{
}
