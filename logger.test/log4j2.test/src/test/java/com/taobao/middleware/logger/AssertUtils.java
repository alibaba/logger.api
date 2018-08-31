package com.taobao.middleware.logger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;

import com.taobao.middleware.logger.support.LoggerHelper;

public class AssertUtils {

    public static void assertFileExist(String file, int start, int end, String dataPattern) {
        if (dataPattern != null) {
            DateFormat df = new SimpleDateFormat(dataPattern);
            String today = df.format(new Date());

            File f1 = new File(file);
            Assert.assertTrue(f1.exists() && f1.isFile());

            file = file + "." + today + ".";
        } else {
            file = file + ".";
        }

        for (int i = start; i <= end; i++) {
            File f = new File(file + i);
            Assert.assertTrue(f.exists() && f.isFile());
        }
    }

    public static void assertFileExist(String file, int start, int end) {
        assertFileExist(file, start, end, "yyyy-MM-dd");
    }

    public static void assertEndWith(String expected, String actual) {
        System.out.println("expected: " + expected + "\nauctual: " + actual);
        Assert.assertTrue("expected: " + expected + ", auctual: " + actual, actual != null && actual.endsWith(expected));
    }

    public static void assertContain(String expected, String actual) {
        System.out.println("expected: " + expected + "\nauctual: " + actual);
        Assert.assertTrue("expected: " + expected + ", auctual: " + actual, actual != null && actual.contains(expected));
    }

    public static void assertNotNull(String actual) {
        System.out.println("actual: " + actual);
        Assert.assertNotNull("auctual: " + actual, actual);
    }

    public static void prepareLogDir() {
        File logs = new File(LoggerHelper.getLogpath());
        deleteFile(logs);
    }

    public static void deleteFile(File dir) {
        if (dir.isDirectory() && dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    System.out.println("delete file: " + file.getAbsolutePath());
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteFile(file);

                }
            }
            System.out.println("delete file: " + dir.getAbsolutePath());
            dir.delete();// 删除目录本身
        }
    }

    public static String readNewLine(String log) {
        System.out.println("log file path: " + log);
        try {
            List<String> lines = FileUtils.readLines(new File(log));
            String line = null;
            int i = lines.size();
            while (i > 0) {
                line = lines.get(--i);
                if (line == null || line.trim().equals("")) {
                    continue;
                }

                return line;
            }
        } catch (IOException e) {
        }

        return null;
    }

    public static void sleep() {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
