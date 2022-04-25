package com.example.brickscrapper.config;

import com.example.brickscrapper.constant.tokopedia.TokopediaQueryParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "scrapper")
public class ScrapperConfiguration {

    private Tokopedia tokopedia;

    @Getter
    @Setter
    public static class Tokopedia {
        private String urlPath = "p/handphone-tablet/handphone";
        private String ipAddress = "47.47.244.18";
        private Product product;

        @Getter
        @Setter
        public static class Product {
            private TokopediaQueryParam sortBy = TokopediaQueryParam.SORTING_REVIEWS_QUERY_VALUE;

            /**
             * Total product to fetch
             */
            @Min(1)
            @Max(1000)
            private int count = 100;

            @Min(1)
            private int maxResultInPage = 60;

            private String fileOutputPath = "tokopedia_top_products.csv";
        }
    }
}
