package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

public class Slf4jLogbackResourceBundleTest {

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Test
    public void testRolling() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Locale.setDefault(Locale.ENGLISH);

        LoggerHelper.setResourceBundle("notify", "notify");
        logger.activateAppenderWithSizeRolling("notify", "client/notify0.log", "GBK", "50KB", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        logger.info("1", "info1", 1);

        AssertUtils.assertEndWith("test rolling with 50KB size 1",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/notify0.log")));
    }

    @Test
    public void testRolling2() {
        Locale.setDefault(Locale.CHINA);
        LoggerHelper.setResourceBundle("notify", "notify");

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.activateAppenderWithSizeRolling("notify", "client/notify-1.log", "GBK", "50KB", 3);
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        logger.info("1", "info1", 1);

        AssertUtils.assertEndWith("test chinese rolling with 50KB size 1",
                                  AssertUtils.readNewLine(LoggerHelper.getLogFile("notify", "client/notify-1.log")));
    }
}
