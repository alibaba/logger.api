package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Log4j2SizeRollingTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppenderWithSizeRolling("notify", "client/size-rolling.log", "GBK", "50KB", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testRolling() {
        int i = 0;
        while (i++ < 2000) {
            logger.info("1", "test rolling with 50KB size " + i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify", "client/size-rolling.log"), 1, 3, null);
        AssertUtils.assertEndWith("test rolling with 50KB size 2000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/size-rolling.log")));
    }
}
