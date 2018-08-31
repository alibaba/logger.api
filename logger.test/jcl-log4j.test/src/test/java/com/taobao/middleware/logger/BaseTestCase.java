package com.taobao.middleware.logger;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.taobao.middleware.logger.support.LoggerHelper;

/**
 * 各种使用场景，请自行复制此类
 * 
 */
public abstract class BaseTestCase {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String productName;
    protected String file;

    protected abstract String getLogImpl();

    @Test
    public void testDebug() {
        logger.setLevel(Level.DEBUG);

        // 验证debug format输出
        String format = "this is " + getLogImpl() + " debug info @ {}";
        Date now = new Date();
        logger.debug(format, now);
        AssertUtils.assertEndWith("this is " + getLogImpl() + " debug info @ " + now.toString(),
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证debug正常输出，带context输出
        String one = "1 this is " + getLogImpl() + " debug info @ " + new Date().toString();
        logger.debug("loginId=debug001", one);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[loginId=debug001]",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("] [] [] ", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证level是info或以上时，debug信息不输出
        String two = "2 this is " + getLogImpl() + " debug info @ " + new Date().toString();
        logger.setLevel(Level.INFO);
        logger.debug(two);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        logger.setLevel(Level.WARN);
        logger.debug(two);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        logger.setLevel(Level.ERROR);
        logger.debug(two);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
    }

    @Test
    public void testInfo() {
        logger.setLevel(Level.INFO);

        // 验证info format输出
        String format = "this is " + getLogImpl() + " info info @ {}";
        Date now = new Date();
        logger.info(format, now);
        AssertUtils.assertEndWith("this is " + getLogImpl() + " info info @ " + now.toString(),
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证info正常输出，带context
        String one = "1 this is " + getLogImpl() + " info info @ " + new Date().toString();
        logger.info("loginId=info001", one);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[loginId=info001]",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("] [] [] ", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证debug级别下info正常输出
        String two = "2 this is " + getLogImpl() + " info info @ " + new Date().toString();
        logger.setLevel(Level.DEBUG);
        logger.info("", two);
        AssertUtils.assertEndWith(two, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证warn级别下info不输出
        String three = "3 this is " + getLogImpl() + " info info @ " + new Date().toString();
        logger.setLevel(Level.WARN);
        logger.info(three);
        AssertUtils.assertEndWith(two, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证error级别下info不输出
        logger.setLevel(Level.ERROR);
        logger.info(three);
        AssertUtils.assertEndWith(two, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
    }

    @Test
    public void testWarn() {
        logger.setLevel(Level.WARN);

        // 验证warn format输出
        String format = "this is " + getLogImpl() + " warn info @ {}";
        Date now = new Date();
        logger.warn(format, now);
        AssertUtils.assertEndWith("this is " + getLogImpl() + " warn info @ " + now.toString(),
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证warn正常输出，带context
        String zero = "0 this is " + getLogImpl() + " warn info @ " + new Date().toString();
        logger.warn("loginId=warn001", zero);
        AssertUtils.assertEndWith(zero, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[loginId=warn001]",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("] [] [] ", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证debug级别下warn正常输出
        String one = "1 this is " + getLogImpl() + " warn info @ " + new Date().toString();
        logger.setLevel(Level.DEBUG);
        logger.warn(one);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证info级别下warn正常输出
        String two = "2 this is " + getLogImpl() + " warn info @ " + new Date().toString();
        logger.setLevel(Level.INFO);
        logger.warn(two);
        AssertUtils.assertEndWith(two, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证warn级别下warn正常输出
        String three = "3 this is " + getLogImpl() + " warn info @ " + new Date().toString();
        logger.setLevel(Level.WARN);
        logger.warn(three);
        AssertUtils.assertEndWith(three, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证error级别下warn不能输出
        String four = "4 this is " + getLogImpl() + " warn info @ " + new Date().toString();
        logger.setLevel(Level.ERROR);
        logger.warn(four);
        AssertUtils.assertEndWith(three, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
    }

    @Test
    public void testError() {
        logger.setLevel(Level.ERROR);

        // 验证error format输出
        String format = "this is " + getLogImpl() + " error info @ {},{}";
        Date now = new Date();
        logger.error("loginId=xxx002", productName.toUpperCase() + "-0001", format, now, "just a param");
        AssertUtils.assertEndWith("this is " + getLogImpl() + " error info @ " + now.toString() + ",just a param",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证error正常输出，带context，errorCode
        String zero = "this is " + getLogImpl() + " error info @" + new Date().toString();
        logger.error("loginId=error001", productName.toUpperCase() + "-0001", zero);
        AssertUtils.assertEndWith(zero, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        AssertUtils.assertContain("[loginId=error001]",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[" + productName.toUpperCase() + "-0001]",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("] [] [", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证warn带context和异常
        logger.error("loginId=error002", productName.toUpperCase() + "-0002", "this is " + getLogImpl()
                + " error info @ " + new Date().toString(), new IllegalArgumentException("just test"));
        AssertUtils.assertContain("at ", AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证debug级别下error输出
        String one = "1 this is " + getLogImpl() + " error info @ " + new Date().toString();
        logger.setLevel(Level.DEBUG);
        logger.error(productName.toUpperCase() + "-0003", one);
        AssertUtils.assertEndWith(one, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[] [] [" + productName.toUpperCase() + "-0003] ",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证info级别下error输出
        String two = "2 this is " + getLogImpl() + " error info @ " + new Date().toString();
        logger.setLevel(Level.INFO);
        logger.error("loginId=error002", productName.toUpperCase() + "-0004", two);
        AssertUtils.assertEndWith(two, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[loginId=error002] [] [" + productName.toUpperCase() + "-0004] ",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证warn级别下error输出
        String three = "3 this is " + getLogImpl() + " error info @ " + new Date().toString();
        logger.setLevel(Level.WARN);
        logger.error(productName.toUpperCase() + "-0005", three);
        AssertUtils.assertEndWith(three, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[] [] [" + productName.toUpperCase() + "-0005] ",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));

        // 验证error级别下error能输出
        String four = "4 this is " + getLogImpl() + " error info @ " + new Date().toString();
        logger.setLevel(Level.ERROR);
        logger.error(productName.toUpperCase() + "-0006", four);
        AssertUtils.assertEndWith(four, AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
        AssertUtils.assertContain("[] [] [" + productName.toUpperCase() + "-0006] ",
                AssertUtils.readNewLine(LoggerHelper.getLogFileP(productName, file)));
    }

    @BeforeClass
    public static void deleteLogDir() {
        AssertUtils.prepareLogDir();
    }
}
