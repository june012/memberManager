package org.guess.sys.dao.impl;

import org.guess.core.orm.hibernate.HibernateDao;
import org.guess.sys.dao.SuggestionLogDao;
import org.guess.sys.model.SuggestionLog;
import org.springframework.stereotype.Repository;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Repository
public class SuggestionLogDaoImpl extends HibernateDao<SuggestionLog,Long> implements SuggestionLogDao {
}
