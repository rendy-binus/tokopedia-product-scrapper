package com.example.brickscrapper.util;

import java.util.Map;

public class URLUtil {

    public static String toQueryParamString(Map<String, String> queryParams) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        return sb.toString();
    }

}
