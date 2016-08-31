package org.guess.core.orm.hibernate;

import com.google.common.collect.Maps;
import org.guess.core.orm.Page;
import org.guess.core.orm.PageRequest;
import org.guess.core.utils.AssertUtils;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by rguess on 2015/2/5.
 */
public class NativeSqlQueryHibernateDao<T, ID extends Serializable> extends HibernateDao<T, ID> {

//    private SessionFactory factory;
//
//    public SessionFactory getFactory() {
//        return factory;
//    }
//
//    public void setFactory(SessionFactory factory) {
//        this.factory = factory;
//    }

    public Page<T> findPageBySql(final PageRequest pageRequest, String sql,
                                 final Object... values) {

        AssertUtils.notNull(pageRequest, "pageRequest不能为空");

        Page<T> page = new Page<T>(pageRequest);
        if (pageRequest.isCountTotal()) {
            long totalCount = countSqlResult(sql, values);
            page.setTotalItems(totalCount);
        }

        SQLQuery query = createSqlQuery(sql, values);
        query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());

        List result = query.addEntity(entityClass).list();
        page.setResult(result);

        return page;
    }

    public Map findMapBySql(final PageRequest pageRequest, String sql,
                                  final Object... values) {

        Map map = Maps.newHashMap();
        AssertUtils.notNull(pageRequest, "pageRequest不能为空");
        if (pageRequest.isCountTotal()) {
            long totalCount = countSqlResult(sql, values);
            map.put("totalCount", totalCount);
        }
        SQLQuery query = createSqlQuery(sql, values);
        query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());
        List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        map.put("list",list);
        return map;
    }

    protected long countSqlResult(final String sql, final Object... values) {
        String countSql = prepareCountHql(sql);

        try {
            Long count = ((BigInteger) findUniqueBySql(countSql, values)).longValue();
            return count;
        } catch (Exception e) {
            throw new RuntimeException("sql can't be auto count, sql is:"
                    + countSql, e);
        }
    }

    public <X> X findUniqueBySql(final String sql, final Object... values) {
        return (X) createSqlQuery(sql, values).uniqueResult();
    }

    public SQLQuery createSqlQuery(String sql, Object[] values) {
        AssertUtils.hasText(sql, "sql不能为空");
        SQLQuery query = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

}
