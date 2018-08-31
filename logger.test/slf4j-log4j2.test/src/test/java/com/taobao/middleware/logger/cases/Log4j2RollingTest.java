package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Log4j2RollingTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Logger logger2 = LoggerFactory.getLogger("a.b.c");

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithTimeAndSizeRolling("notify", "client/rolling.log", "GBK", "50KB", "yyyy-MM-dd", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        logger2.activateAppender("notify", "client/default.log", "GBK");
        logger2.setAdditivity(false);
        logger2.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() throws InterruptedException {
        int i = 0;
        while (i++ < 2000) {
            logger.info("1", "test rolling with 50KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify", "client/rolling.log"), 1, 3, "yyyy-MM-dd");
        AssertUtils.assertEndWith("test rolling with 50KB size 2000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/rolling.log")));
    }

    @Test
    public void testDefaultRolling() throws InterruptedException {
        int i = 0;
        while (i++ < 2000) {
            logger2.info("1", "test rolling by data " + i);
        }

        AssertUtils.assertEndWith("test rolling by data 2000",
                AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/default.log")));
    }
}
