package com.danthy.pizzafun.app.utils;

import java.io.File;
import java.nio.file.FileStore;

public class PathUtil {
    public static String getRootPath() {
        return System.getProperty("user.dir") + File.separator +
                "src" +
                File.separator +
                "main";
    }

    public static String getTargetPath() {
        return System.getProperty("user.dir") + File.separator +
                "target" +
                File.separator +
                "classes";
    }
}
