package com.taobao.middleware.logger.cases;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.taobao.middleware.logger.BaseTestCase;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;

public class Slf4jLog4jTest extends BaseTestCase {

    private static Field loggerField = null;

    static {
        try {
            loggerField = org.slf4j.impl.Log4jLoggerAdapter.class.getDeclaredField("logger");
            loggerField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("logger must be instanceof org.slf4j.impl.Log4jLoggerAdapter", e);
        }
    }

    @Before
    public void prepare() {
        productName = "tddl";
        file = "client/tddl.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
    }

    @Test
    public void testImpl() {
        Assert.assertTrue(logger.getDelegate().getClass().getName()
                .equals(org.slf4j.impl.Log4jLoggerAdapter.class.getName()));
        logger.setAdditivity(false);
    }

    @Test
    public void testAppender() throws IllegalArgumentException, IllegalAccessException {
        Logger logger = LoggerFactory.getLogger("a.b.c");
        logger.activateAppender(this.logger);

        Assert.assertEquals(logger.getDelegate().getClass(), this.logger.getDelegate().getClass());

        Assert.assertEquals("org.slf4j.impl.Log4jLoggerAdapter", logger.getDelegate().getClass()
                .getName());

        org.apache.log4j.Logger log4jLogger =
                (org.apache.log4j.Logger) loggerField.get(logger.getDelegate());
        org.apache.log4j.Logger log4jThisLogger =
                (org.apache.log4j.Logger) loggerField.get(((Logger) this.logger).getDelegate());

        Assert.assertEquals(log4jLogger.getAllAppenders().nextElement(), log4jThisLogger
                .getAllAppenders().nextElement());
    }

    @Override
    protected String getLogImpl() {
        return "slf4j + log4j12";
    }
}
