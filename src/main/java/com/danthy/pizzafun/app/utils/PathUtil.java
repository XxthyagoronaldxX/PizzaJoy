package com.danthy.pizzafun.app.utils;

import java.io.File;

public class PathUtil {
    public static String getRootPath() {
        return System.getProperty("user.dir") + File.separator +
                "src" +
                File.separator +
                "main";
    }
}
