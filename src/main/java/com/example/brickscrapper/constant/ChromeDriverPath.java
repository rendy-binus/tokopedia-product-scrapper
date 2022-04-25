package com.example.brickscrapper.constant;

public enum ChromeDriverPath {
    WINDOWS("/chromedriver_win32/chromedriver.exe"),
    UNIX("/chromedriver_linux64/chromedriver"),
    MAC64("/chromedriver_mac64/chromedriver"),
    MAC64_M1("/chromedriver_mac64_m1/chromedriver")
    ;

    private final String path;

    ChromeDriverPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
