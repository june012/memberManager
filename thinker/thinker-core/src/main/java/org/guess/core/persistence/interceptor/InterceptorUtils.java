package org.guess.core.persistence.interceptor;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.guess.core.persistence.Page;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by garyr on 2015/4/26.
 */
public class InterceptorUtils {

    /*dao方法中参数名称*/
    public final static String DB_NAME = "dbname";

    /*用于sql被替换的特殊字符*/
    public final static String SQL_REP_STR = "@db_name";

    /**
     * @param ms
     * @param newSqlSource
     * @return
     */
    public static MappedStatement copyFromMappedStatement(MappedStatement ms,
                                                          SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null) {
            for (String keyProperty : ms.getKeyProperties()) {
                builder.keyProperty(keyProperty);
            }
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        return builder.build();
    }

    /**
     * 获取真实数据库名称,并插入真实dbname
     *
     * @param invocation
     * @return
     */
    public static String isertDBName(Invocation invocation, String sql) {
        StringBuffer sqlbuffer = new StringBuffer(sql);
        MapperMethod.ParamMap paramMap = null;
        Object[] objects = invocation.getArgs();
        for (Object obj : objects) {
            if (obj instanceof MapperMethod.ParamMap) {
                paramMap = (MapperMethod.ParamMap) obj;
                break;
            }
        }
        if (paramMap != null && paramMap.containsKey(DB_NAME)) {
            String dbname = paramMap.get(DB_NAME).toString();
            Pattern p = Pattern
                    .compile(
                            "(?i)(?<=(?:from|into|update|join)\\s{1,1000}"
                                    + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                    + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                    + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                    + ")(\\w+)"
                    );
            Matcher m = p.matcher(sql);
            dbname = dbname + ".";
            int index = 0;
            while (m.find()) {
                //去掉dual
                if ("dual".equalsIgnoreCase(m.group())) {
                    continue;
                }
                sqlbuffer.insert(m.end() - m.group().length() + index * dbname.length(), dbname);
                index += 1;
            }
            return sqlbuffer.toString();
        }
        return sqlbuffer.toString();
    }

    /**
     * 获取page对象
     *
     * @param parameter
     * @return
     */
    public static Page getPageObj(Object parameter) {

        if (parameter == null) {
            return null;
        }

        if (parameter instanceof Page) {
            return (Page) parameter;
        }

        MapperMethod.ParamMap paramMap = null;
        if (parameter instanceof MapperMethod.ParamMap) {
            paramMap = (MapperMethod.ParamMap) parameter;
        }

        if (paramMap != null && paramMap.containsKey("page")) {
            return (Page) paramMap.get("page");
        }

        return null;
    }
}
