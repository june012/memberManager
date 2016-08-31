package org.guess.modules.test.mapper;

import org.apache.ibatis.annotations.Param;
import org.guess.core.persistence.Page;
import org.guess.core.persistence.annotation.MyBatisDao;
import org.guess.modules.test.entity.MybatiesTest;

import java.util.List;

/**
 * Created by garyr on 2015/4/18.
 */
@MyBatisDao
public interface MyBatiesTestDao {

    List<MybatiesTest> list(@Param("obj")MybatiesTest obj,@Param("dbname")String dbname);

    void insert(MybatiesTest obj);

    List<MybatiesTest> findPage(@Param(value = "page") Page<MybatiesTest> page);

    List<MybatiesTest> list1();
}
