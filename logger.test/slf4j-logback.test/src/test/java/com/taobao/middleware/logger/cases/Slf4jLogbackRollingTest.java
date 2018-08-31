package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

public class Slf4jLogbackRollingTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithTimeAndSizeRolling("notify4", "client/notify4.log", "GBK", "5KB");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() {
        int i = 0;
        while (i++ < 8000) {
            logger.info("1", "test rolling with 5KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify4", "client/notify4.log"), 0, 3);
        AssertUtils.assertEndWith("test rolling with 5KB size 8000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify4", "client/notify4.log")));
    }
}
