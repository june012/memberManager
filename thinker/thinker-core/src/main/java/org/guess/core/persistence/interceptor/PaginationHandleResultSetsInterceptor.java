package org.guess.core.persistence.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.io.Serializable;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by garyr on 2015/4/26.
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets",
                args = {Statement.class})})
public class PaginationHandleResultSetsInterceptor implements Interceptor, Serializable {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        for (Object object:objects){
            System.out.println(object);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
