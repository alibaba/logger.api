package com.taobao.middleware.logger;

public class LoggerInit {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LoggerInit.class);
        logger.activateAppender("hsf", "hsf.log", "GBK");
        logger.setLevel(Level.INFO);
        logger.setAdditivity(false);

        logger.info("context", "message {} {}", 1, 2);
        
        // 日志文件内容：
        // 01 2014-03-19 20:55:08.501 INFO [main :logger.LoggerInitTest] [context] [] [] message 1 2
    }
}
