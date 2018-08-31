/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.taobao.middleware.logger;

import com.taobao.middleware.logger.support.LoggerConfig;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.taobao.middleware.logger.support.ErrorLog;

/**
 */
public class ErrorLogTest extends TestCase {

    @Test
    public void test() {
        Assert.assertEquals(
                "xx ERR-CODE: [JW-0001], Type: [SystemError], More: [" + LoggerConfig.MORE_INFO_URL
                    + "help/JW-0001]",
                ErrorLog.buildErrorMsg("xx", "JW-0001", "SystemError"));
    }
}
