package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

public class Log4jResourceBundleTest {

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Test
    public void testRolling() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Locale.setDefault(Locale.ENGLISH);

        LoggerHelper.setResourceBundle("notify", "notify");
        logger.activateAppenderWithSizeRolling("notify", "client/notify.log", "GBK", "50KB", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        int i = 0;
        while (i++ < 2000) {
            logger.info("1", "info1", i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify", "client/notify.log"), 1, 3, null);
        AssertUtils.assertEndWith("test rolling with 50KB size 2000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/notify.log")));
    }

    @Test
    public void testRolling2() {
        Locale.setDefault(Locale.CHINA);
        LoggerHelper.setResourceBundle("notify", "notify");

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.activateAppenderWithSizeRolling("notify", "client/notify.log", "GBK", "50KB", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        int i = 0;
        while (i++ < 2000) {
            logger.info("1", "info1", i);
        }

        AssertUtils.assertFileExist(LoggerHelper.getLogFile("notify", "client/notify.log"), 1, 3, null);
        AssertUtils.assertEndWith("test chinese rolling with 50KB size 2000",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/notify.log")));
    }
}
