package com.taobao.middleware.logger.cases;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.taobao.middleware.logger.BaseTestCase;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;

public class Log4jTest extends BaseTestCase {

    @Before
    public void prepare() {
        productName = "hsf";
        file = "hsf.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
    }

    @Test
    public void testImpl() {
        Assert.assertTrue(logger.getDelegate().getClass().getName()
                .equals(org.apache.log4j.Logger.class.getName()));
    }

    @Test
    public void testAppender() {
        Logger logger = LoggerFactory.getLogger("a.b.c");
        logger.activateAppender(this.logger);

        Assert.assertEquals(logger.getDelegate().getClass(), this.logger.getDelegate().getClass());

        Assert.assertEquals("org.apache.log4j.Logger", logger.getDelegate().getClass().getName());

        Assert.assertEquals(((org.apache.log4j.Logger) logger.getDelegate()).getAllAppenders()
                .nextElement(), ((org.apache.log4j.Logger) this.logger.getDelegate()).getAllAppenders()
                .nextElement());
    }

    @Override
    protected String getLogImpl() {
        return "log4j";
    }
}
