package com.taobao.middleware.logger.cases;

import org.junit.Before;
import org.junit.Test;

import com.taobao.middleware.logger.Level;
import com.taobao.middleware.logger.Logger;
import com.taobao.middleware.logger.LoggerFactory;

public class Log4jConsoleTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void prepare() {
        logger.activateConsoleAppender("System.err", "GBK");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testAppender() {
        logger.error("XX", "abc");

        logger.warn("YY", new NullPointerException("dummy exception"));
    }

}
