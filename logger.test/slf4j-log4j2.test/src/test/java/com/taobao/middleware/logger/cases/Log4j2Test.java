package com.taobao.middleware.logger.cases;

import com.taobao.middleware.logger.*;
import com.taobao.middleware.logger.support.LoggerHelper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class Log4j2Test extends BaseTestCase {

    @Before
    public void prepare() {
        productName = "hsf";
        file = "hsf.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
    }

    @Test
    public void testImpl() {
        Assert.assertEquals("org.apache.logging.slf4j.Log4jLogger", logger.getDelegate().getClass().getName());
    }

    @Test
    public void testAppender() {
        Logger logger = LoggerFactory.getLogger("a.b.c");
        logger.activateAppender(this.logger);
        logger.setLevel(Level.INFO);

        logger.info("this is activateAppender log message");
        AssertUtils.assertContain("this is activateAppender log message", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        Assert.assertEquals(logger.getDelegate().getClass(), this.logger.getDelegate().getClass());

        Assert.assertEquals("org.apache.logging.slf4j.Log4jLogger", logger.getDelegate().getClass().getName());
    }

    @Override
    protected String getLogImpl() {
        return "slf4j-log4j2";
    }
}
