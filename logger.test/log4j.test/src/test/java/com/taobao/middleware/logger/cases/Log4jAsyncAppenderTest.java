package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

public class Log4jAsyncAppenderTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Logger aaa = LoggerFactory.getLogger("aaa");

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAsyncAppender("log4j", "log4j.log", "GBK");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        aaa.activateConsoleAppender("System.out", "GBK");
        List<Object[]> args = new ArrayList<Object[]>();
        args.add(new Object[] { "setBufferSize", new Class<?>[] { int.class }, 256 });
        aaa.activateAsync(args);
        aaa.setAdditivity(false);
        aaa.setLevel(Level.INFO);
    }

    @Test
    public void test() {
        int i = 0;
        while (i++ < 1000) {
            logger.info("1", "test async appender " + i);
        }

        AssertUtils.sleep(2000);
        AssertUtils.assertEndWith("test async appender 1000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("log4j", "log4j.log")));
    }

    @Test
    public void test2() {
        int i = 0;
        while (i++ < 1000) {
            aaa.info("1", "test async appender " + i);
        }
    }
}
