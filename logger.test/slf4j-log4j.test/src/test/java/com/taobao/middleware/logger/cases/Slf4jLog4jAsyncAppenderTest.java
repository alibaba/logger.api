package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

public class Slf4jLog4jAsyncAppenderTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAsyncAppender("slf4j", "log4j/log4j.log", "GBK");
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
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("slf4j", "log4j/log4j.log")));
    }
}
