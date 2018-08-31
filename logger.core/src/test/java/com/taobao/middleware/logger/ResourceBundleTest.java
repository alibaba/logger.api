package com.taobao.middleware.logger;

import com.taobao.middleware.logger.support.LoggerHelper;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by zhuyong on 15/3/3.
 */
public class ResourceBundleTest {

    @Test
    public void test1() {
        LoggerHelper.setResourceBundle("HSF", "hsf");
        System.out.println(LoggerHelper.getResourceBundleString("HSF", "1"));

        Locale.setDefault(Locale.ENGLISH);
        LoggerHelper.setResourceBundle("HSF", "hsf");

        Assert.assertEquals("ABC", LoggerHelper.getResourceBundleString("HSF", "1"));
    }
}
