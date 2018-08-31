package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

public class Slf4jLogbackRolling4Test {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithSizeRolling("notify3", "client/notify3.log", "GBK", "5KB", 5);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() {
        int i = 0;
        while (i++ < 1000) {
            logger.info("1", "test rolling with 5KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify3", "client/notify3.log"), 1, 4, null);
        AssertUtils.assertEndWith("test rolling with 5KB size 1000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify3", "client/notify3.log")));
    }
}
