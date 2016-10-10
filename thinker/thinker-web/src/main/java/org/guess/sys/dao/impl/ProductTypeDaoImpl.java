package org.guess.sys.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.sys.dao.ProductTypeDao;
import org.guess.sys.model.ProductType;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Repository
public class ProductTypeDaoImpl extends HibernateDao<ProductType,Long> implements ProductTypeDao {
}
