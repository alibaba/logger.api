package com.taobao.middleware.logger;

import com.taobao.middleware.logger.support.LoggerHelper;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

public class LogpathTest extends TestCase {

    @Test
    public void testDefault() {
        String path = LoggerHelper.getLogpath();

        String logs = File.separator + "logs" + File.separator;
        assertTrue(path.endsWith(logs) && !path.startsWith(logs));
    }

    public static void main(String[] args) {
        String dir = "hehe";
        System.setProperty("JM.LOG.PATH", dir);
        String path = LoggerHelper.getLogpath();
        assertTrue(path.equals(System.getProperty("user.home") + File.separator + dir + File.separator));
    }
}
