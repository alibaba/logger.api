package com.taobao.middleware.logger;

public class LoggerInit {

    public static void main(String[] args) {
        AssertUtils.prepareLogDir();

        Logger logger = LoggerFactory.getLogger(LoggerInit.class);
        logger.activateAppenderWithTimeAndSizeRolling("notify", "client/notify.log", "GBK", "5KB");
        logger.setAdditivity(false);
        logger.setLevel(Level.INFO);

        int i = 0;
        while (i++ < 1000) {
            logger.info("1", "test rolling with 5KB size " + i);
            AssertUtils.sleep(100);
        }
    }

}
