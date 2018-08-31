package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;
import com.taobao.middleware.logger.support.LoggerInfo;
import com.taobao.middleware.logger.support.Log4jHelper;
import com.taobao.middleware.logger.support.LogbackHelper;
import com.taobao.middleware.logger.support.LoggerHelper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SlfLog4jExceptionTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean notset = true;

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }

    @Before
    public void prepare() {
        logger.activateAppender("slf4jlog4j", "slf4jlog4j.log", "GBK");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testException() {
        int i = 0;
        while (i++ < 2000) {
            logger.error("1", "test rolling with 50KB size " + i, new RuntimeException());

            if (i > 200 && notset) {
                notset = false;
                Log4jHelper.setDepth(2);
                LogbackHelper.setDepth(2);
            }
            if (i == 1998) {
                AssertUtils.assertEndWith("at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)",
                                          AssertUtils.readNewLine(
                                                  LoggerHelper.getLogFile("slf4jlog4j", "slf4jlog4j.log")));
                Log4jHelper.setDepth(-1);
                LogbackHelper.setDepth(-1);
            }
        }
        Assert.assertNotSame("at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)",
                             AssertUtils.readNewLine(LoggerHelper.getLogFile("slf4jlog4j", "slf4jlog4j.log")));
    }

    @Test
    public void testPaths() {
        Map<String, LoggerInfo> map = Log4jHelper.getLoggers(null);
        System.out.println(map);
        Assert.assertTrue(!map.isEmpty());

        map = LogbackHelper.getLoggers(null);
        System.out.println(map);
        Assert.assertTrue(map.isEmpty());
    }
}
