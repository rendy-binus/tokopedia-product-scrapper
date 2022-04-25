package com.example.brickscrapper.constant.tokopedia;

public enum TokopediaElementXPath {
    PRODUCT_LIST("//div[@data-testid='lstCL2ProductList']/div"),
    PRODUCT_LINK("a[@data-testid='lnkProductContainer']"),
    PRODUCT_IMAGE_SLIDER("//div[@data-testid='listPDPSlider']"),
    PRODUCT_IMAGE_THUMBNAIL("//div[@data-testid='PDPImageThumbnail']/div/img[not(contains(@src, \"data:image\"))]"),
    PRODUCT_CONTENT("//div[@id='pdp_comp-product_content']"),
    PRODUCT_NAME("//h1[@data-testid='lblPDPDetailProductName']"),
    PRODUCT_PRICE("//div[@data-testid='lblPDPDetailProductPrice']"),
    PRODUCT_RATING("//span/span[@data-testid='lblPDPDetailProductRatingNumber']"),
    PRODUCT_PAGE_CONTAINER("//div[@id='main-pdp-container']"),
    PRODUCT_DESCRIPTION("//div[@data-testid='lblPDPDescriptionProduk']"),
    MERCHANT_NAME("//a[@data-testid='llbPDPFooterShopName']/h2"),
    ROOT("//div[@id='zeus-root']"),
    LOADER("//span[@data-unify='LoaderLine']")
    ;

    private final String query;

    TokopediaElementXPath(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
