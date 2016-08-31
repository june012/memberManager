package thinker.dao;

import org.guess.core.orm.Page;
import org.guess.core.orm.PageRequest;
import org.guess.core.utils.mapper.JaxbMapper;
import org.guess.core.utils.mapper.JsonMapper;
import org.guess.showcase.qixiu.dao.impl.RecordDaoImpl;
import org.guess.showcase.qixiu.model.Record;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by rguess on 2015/2/5.
 */
public class NativeSqlTest {

    private SessionFactory sessionFactory;
    private ApplicationContext ctx;
    RecordDaoImpl dao;

    @Before
    public void before() {
        ctx = new FileSystemXmlApplicationContext("classpath:spring-config-test.xml");
        sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        dao = (RecordDaoImpl) ctx.getBean("recordDaoImpl");
//        dao.setFactory(sessionFactory);
    }

    @Test
    public void test01() {
        Page page = dao.findPageBySql(new Page<Record>(), "select * from q_record");
        System.out.println(JaxbMapper.toXml(page.getResult(),"records", Record.class, "UTF-8"));

    }

    @Test
    public void test02(){
        Map map = dao.findMapBySql(new PageRequest(1,10), "select * from q_record where id = ?",21);

        System.out.println();
        List list = (List)(map.get("list"));
        Map obj = (Map) list.get(0);
        for (Object key : obj.keySet()){
            System.out.println(obj.get(key));
        }
        System.out.println(JsonMapper.nonDefaultMapper().toJson(map));
    }

    @Test
    public void test03(){
        Object[] uniq = dao.findUniqueBySql("select r.id,rd.leibie from q_record r inner join q_record_detail rd on r.id = rd.record_id where r.id = 21");
        System.out.println(uniq[0]);
        System.out.println(uniq[1]);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(uniq));

    }

    @Test
    public void test04(){
        Object[] object = dao.findUniqueBySql("select * from act_ge_property where name_ = ?","next.dbid");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(object));
        System.out.println((Integer) (object[2]));

    }
}
