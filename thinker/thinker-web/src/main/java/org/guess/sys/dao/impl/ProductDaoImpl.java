package org.guess.sys.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.sys.dao.ProductDao;
import org.guess.sys.model.Product;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Repository
public class ProductDaoImpl extends HibernateDao<Product,Long> implements ProductDao {
}
