package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;

public class Slf4jLogbackRolling3Test {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithTimeAndSizeRolling("notify2", "client/notify2.log", "GBK", "50KB", "yyyy-MM-dd", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() {
        int i = 0;
        while (i++ < 5000) {
            logger.info("1", "test rolling with 50KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify2", "client/notify2.log"), 0, 3, "yyyy-MM-dd");
        AssertUtils.assertEndWith("test rolling with 50KB size 5000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify2", "client/notify2.log")));
    }
}
