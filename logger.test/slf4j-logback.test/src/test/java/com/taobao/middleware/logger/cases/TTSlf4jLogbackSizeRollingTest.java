package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

public class TTSlf4jLogbackSizeRollingTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithSizeRolling("notify6", "client/notify6.log", "GBK", "1MB", 2);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() {
        int i = 0;
        while (i++ < 50000) {
            logger.info("1", "test rolling with 5KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify6", "client/notify6.log"), 1, 2, null);
        AssertUtils.assertEndWith("test rolling with 5KB size 50000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify6", "client/notify6.log")));
    }
}
