package com.taobao.middleware.logger.cases;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.taobao.middleware.logger.BaseTestCase;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;

public class Slf4jLogbackTest extends BaseTestCase {

    @Before
    public void prepare() {
        productName = "notify5";
        file = System.currentTimeMillis() + "client/notify5.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
    }

    @Test
    public void testImpl() {
        Assert.assertTrue(logger.getDelegate().getClass().getName()
                .equals(ch.qos.logback.classic.Logger.class.getName()));
    }

    @Test
    public void testAppender() {
        Logger logger = LoggerFactory.getLogger("a.b.c");
        logger.activateAppender(this.logger);

        Assert.assertEquals(logger.getDelegate().getClass(), this.logger.getDelegate().getClass());

        Assert.assertEquals("ch.qos.logback.classic.Logger", logger.getDelegate().getClass().getName());

        Assert.assertEquals(((ch.qos.logback.classic.Logger) logger.getDelegate())
                .iteratorForAppenders().next(), ((ch.qos.logback.classic.Logger) this.logger.getDelegate())
                .iteratorForAppenders().next());
    }

    @Override
    protected String getLogImpl() {
        return "slf4j + logback";
    }
}
