package org.guess.showcase.consume.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.showcase.consume.dao.DrawDao;
import org.guess.showcase.consume.model.DrawRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Repository
public class DrawDaoImpl extends HibernateDao<DrawRecord,Long> implements DrawDao {
}
