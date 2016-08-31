package thinker.core.utils;

import org.guess.core.utils.PropertiesLoader;
import org.guess.core.utils.UuidUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Created by rguess on 2015/2/10.
 */
public class ProTest {

    @Test
    public void test01(){
        PropertiesLoader loader = new PropertiesLoader("classpath:application.properties");
        System.out.println(loader.getProperty("jpushmasterSecret"));
    }

    @Test
    public void test02(){
        System.out.println(UUID.randomUUID());
        System.out.println(UuidUtil.uuid2());
        System.out.println(UuidUtil.uuid());
        System.out.println(UuidUtil.randomLong());
        System.out.println(BigInteger.TEN);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(2147483647);
        System.out.println(Long.MAX_VALUE);
        System.out.println(String.valueOf(Long.MAX_VALUE).length());
    }
}
