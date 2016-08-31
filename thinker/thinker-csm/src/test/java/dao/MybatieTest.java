package dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.guess.core.persistence.Page;
import org.guess.core.utils.mapper.JsonMapper;
import org.guess.modules.test.entity.MybatiesTest;
import org.guess.modules.test.mapper.MyBatiesTestDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rguess on 2015/2/5.
 */
public class MybatieTest {

    private ApplicationContext ctx;
    private MyBatiesTestDao dao;
    private DruidDataSource dataSource;
    private FactoryBean factoryBean;

    @Before
    public void before() {
        ctx = new FileSystemXmlApplicationContext("classpath:spring-config.xml");
        dao = (MyBatiesTestDao) ctx.getBean("myBatiesTestDao");
        dataSource = (DruidDataSource) ctx.getBean("dataSource");
//        factoryBean = (FactoryBean) ctx.getBean("sqlSessionFactory");
    }

    @Test
    public void query() {
        MybatiesTest obj = new MybatiesTest();
        obj.setLoginId("hello");
        List<MybatiesTest> list = dao.list(obj, "test");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(list));
    }

    @Test
    public void insert() {
        MybatiesTest obj = new MybatiesTest(3, "hello3", "123", "guess", "22@ss.com", "123456789", "成都", 0, "remark", new Date());
        dao.insert(obj);
    }

    @Test
    public void page() {
        List<MybatiesTest> page = dao.findPage(new Page<MybatiesTest>(1, 1));
        System.out.println(JsonMapper.nonDefaultMapper().toJson(page));
    }

    @Test
    public void list1(){
        List<MybatiesTest> page = dao.list1();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(page));
    }

    @Test
    public void db() throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rs = connection.prepareStatement("SELECT * FROM MyBatiestest").executeQuery();
        if (rs.next()) {
            System.out.println(rs.getString(2));
        }
    }

    @Test
    public void common() {
//        System.out.println(StringUtils.replace("@he sss @he aaa", "@he", "world"));
        String sql = "SELECT * FROM @db_name.MyBatiesTest WHERE loginId = ?";
        Pattern p = Pattern
                .compile(
                        "(?i)(?<=(?:from|into|update|join)\\s{1,1000}"
                                + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                + ")(\\w+)"
                );
        Matcher m = p.matcher(sql);
        while (m.find()){
            System.out.println(m.group());
        }
    }


}
