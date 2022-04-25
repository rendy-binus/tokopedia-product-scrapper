package com.example.brickscrapper.service;

import com.example.brickscrapper.model.BaseProduct;

import java.util.List;

public interface ScrapperService<T extends BaseProduct> {

    /**
     * <p>
     *     Start scrapping with default <code>baseUrl + urlPath</code>
     * </p>
     * <p>
     *     <code>baseUrl</code> defined {@linkplain com.example.brickscrapper.constant.URLString`}
     * </p>
     * <p>
     *     <code>urlPath</code> defined in {@linkplain com.example.brickscrapper.config.ScrapperConfiguration}
     * </p>
     * @return list of {@linkplain BaseProduct}
     */
    public List<T> startScrapping();


}
