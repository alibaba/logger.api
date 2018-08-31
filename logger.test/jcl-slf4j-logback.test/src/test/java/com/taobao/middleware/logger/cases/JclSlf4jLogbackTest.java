package com.taobao.middleware.logger.cases;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.BaseTestCase;
import com.taobao.middleware.logger.LoggerInit;

public class JclSlf4jLogbackTest extends BaseTestCase {

    @Before
    public void prepare() {
        productName = "metaq";
        file = System.currentTimeMillis()+"metaq.log";
        logger.activateAppender(productName, file, "GBK");
        logger.setAdditivity(false);
    }

    @Test
    public void testLogImpl() {
        Log log = LogFactory.getLog(LoggerInit.class);
        Assert.assertTrue(log.getClass().getName().equals("org.apache.commons.logging.impl.SLF4JLocationAwareLog"));
    }

    @Test
    public void testImpl() {
        Assert.assertTrue(logger.getDelegate().getClass().getName().equals(ch.qos.logback.classic.Logger.class.getName()));
    }

    @Override
    protected String getLogImpl() {
        return "jcl + slf4j + logback";
    }

}
