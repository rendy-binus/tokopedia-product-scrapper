package com.example.brickscrapper.util;

public class OSUtil {
    public static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    public static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    public static final boolean IS_WINDOWS = OS_NAME.contains("win");
    public static final boolean IS_OSX = OS_NAME.contains("mac") && !OS_ARCH.equals("aarch64");
    public static final boolean IS_OSX_ARM = OS_NAME.contains("mac") && OS_ARCH.equals("aarch64");
    public static final boolean IS_AIX = OS_NAME.indexOf("aix") > 0 || OS_NAME.contains("nux") || OS_NAME.contains("nix");

}
