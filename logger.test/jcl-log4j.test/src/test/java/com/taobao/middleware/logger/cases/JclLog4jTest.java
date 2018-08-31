package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.AssertUtils;
import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.support.LoggerHelper;
import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.taobao.middleware.logger.BaseTestCase;
import com.taobao.middleware.logger.LoggerInit;

public class JclLog4jTest extends BaseTestCase {

    @Before
    public void prepare() {
        productName = "config";
        file = "config.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testLogImpl() {
        Log log = LogFactory.getLog(JclLog4jTest.class);
        Assert.assertTrue(log.getClass().getName().equals("org.apache.commons.logging.impl.Log4JLogger"));
        log.info("this is a test message");
        AssertUtils.assertEndWith("this is a test message",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
    }

    @Test
    public void testImpl() {
        Assert.assertTrue(logger.getDelegate().getClass().getName().equals(org.apache.log4j.Logger.class.getName()));
    }

    @Override
    protected String getLogImpl() {
        return "jcl-log4j";
    }
}
