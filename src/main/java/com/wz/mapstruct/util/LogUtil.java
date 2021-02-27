package com.wz.mapstruct.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: LogUtil
 * @description:
 * @author: zhi
 * @date: 2021/1/29
 * @version: 1.0
 */
public final class LogUtil {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String PATH = USER_HOME + "/logs/map-struct-code-helper";
    private static final String LOG_FILE = "map-struct-code-helper.log";
    private static final boolean IS_DEBUG = true;
    private static BufferedWriter bw;

    static {
        File file = new File(PATH, LOG_FILE);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            file = new File(USER_HOME, LOG_FILE);
            try {
                bw = new BufferedWriter(new FileWriter(file));
            } catch (IOException ignored) {
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                bw.close();
            } catch (Throwable ignored) {
            }
        }));
    }

    private LogUtil() {
    }

    public static void debug(String msg) {
        if (IS_DEBUG) {
            log(msg);
        }
    }

    public static void log(String msg) {
        log(msg, null);
    }

    public synchronized static void log(String msg, Throwable t) {
        try {
            System.out.println(msg);
            bw.write(msg);
            bw.newLine();
            if (Objects.nonNull(t)) {
                final StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                final String errorMsg = sw.toString();
                System.out.println(errorMsg);
                bw.write(errorMsg);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new IllegalStateException("map-struct code helper debug fail.", e);
        }
    }

    public static void main(String[] args) {
        LogUtil.log("debug .....");
        LogUtil.log("调试MapStruct 插件。");
        LogUtil.log("调试MapStruct 插件， 发生错误。", new RuntimeException("发生异常了。"));
        LogUtil.log("调试MapStruct 插件， 发生错误。", new NullPointerException("空指针异常"));
    }
}
