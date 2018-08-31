package com.taobao.middleware.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerInit {

    public static void main(String[] args) {
        Log log = LogFactory.getLog(LoggerInit.class);

        log.info("this is jcl logger info");
    }
}
