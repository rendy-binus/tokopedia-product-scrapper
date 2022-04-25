package com.example.brickscrapper.constant;

public class URLString {

    public enum Tokopedia {
        BASE_URL("https://wwww.tokopedia.com"),
        ADS_URL("https://ta.tokopedia.com"),
        ;

        private final String val;

        Tokopedia(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

}
