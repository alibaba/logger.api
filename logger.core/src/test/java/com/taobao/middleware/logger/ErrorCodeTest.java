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

import com.taobao.middleware.logger.support.LoggerHelper;

/**
 */
public class ErrorCodeTest extends TestCase {

    private static final String MORE_INFO_URL_FOR_TEST = "";

    public void test1() {
        System.clearProperty("HSF.ERROR_CODE_MORE_URL");
        String r = LoggerHelper.getErrorCodeStr("hsf", "HSF-001", "客户端出错", "服务器地址出错了");
        Assert.assertEquals(
                "服务器地址出错了 ERR-CODE: [HSF-001], Type: [客户端出错], More: [" + LoggerConfig.MORE_INFO_URL
            + "help/HSF-001]", r);
    }

    public void test2() {
        System.setProperty("HSF.ERROR_CODE_MORE_URL", MORE_INFO_URL_FOR_TEST + "jm/");
        String r = LoggerHelper.getErrorCodeStr("hsf", "HSF-001", "客户端出错", "服务器地址出错了");
        Assert.assertEquals(
                "服务器地址出错了 ERR-CODE: [HSF-001], Type: [客户端出错], More: [" + MORE_INFO_URL_FOR_TEST + "jm/HSF-001]", r);
    }
}
