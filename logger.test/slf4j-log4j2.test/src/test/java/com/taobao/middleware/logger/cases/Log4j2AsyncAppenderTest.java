package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Log4j2AsyncAppenderTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAsyncAppender("log4j2", "log4j2.log", "GBK");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void test() {
        int i = 0;
        while (i++ < 1000) {
            logger.info("1", "test async appender " + i);
        }

        AssertUtils.sleep(2000);
        AssertUtils.assertEndWith("test async appender 1000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("log4j2", "log4j2.log")));
    }
}
