package com.example.brickscrapper.constant.tokopedia;

public enum TokopediaQueryParam {
    PAGING_QUERY_NAME("page"),
    SORTING_QUERY_NAME("ob"),
    CITY_FILTER_QUERY_NAME("fcity"),
    SHIPPING_FILTER_QUERY_NAME("shipping"),
    CASHBACK_FILTER_QUERY_NAME("cashbackm"),
    FREE_SHIPPING_FILTER_QUERY_NAME("free_shipping"),
    COD_FILTER_QUERY_NAME("cod"),
    DISCOUNT_FILTER_QUERY_NAME("is_discount"),
    WHOLESALE_FILTER_QUERY_NAME("wholesale"),
    SHOP_TIER_FILTER_QUERY_NAME("shop_tier"),


    SORTING_CHEAPEST_PRICE_QUERY_VALUE("3"),
    SORTING_HIGHEST_PRICE_QUERY_VALUE("4"),
    SORTING_REVIEWS_QUERY_VALUE("5"),
    SORTING_NEWEST_QUERY_VALUE("9"),
    SORTING_MOST_SUITABLE_QUERY_VALUE("23"),


    SHOP_TIER_SEPARATOR_QUERY_VALUE("-"),
    SHOP_TIER_POWER_MERCHANT_QUERY_VALUE("1"),
    SHOP_TIER_OFFICIAL_STORE_QUERY_VALUE("2"),
    SHOP_TIER_POWER_MERCHANT_PRO_QUERY_VALUE("3"),
    ;
    private final String val;

    TokopediaQueryParam(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
