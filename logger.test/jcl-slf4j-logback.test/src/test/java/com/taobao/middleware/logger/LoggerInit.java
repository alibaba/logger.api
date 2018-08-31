package com.taobao.middleware.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerInit {

    public static void main(String[] args) {
        Log log = LogFactory.getLog(LoggerInit.class);

        log.info("this is jcl logger info");

        // 日志文件内容：
        // 01 2014-03-19 20:55:08.501 INFO [main :logger.LoggerInitTest] [context] [] [] message 1 2
    }
}
