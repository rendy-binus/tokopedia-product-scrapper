package com.example.brickscrapper.service;

import com.example.brickscrapper.chromedriver.ChromeDriverFactory;
import com.example.brickscrapper.config.ScrapperConfiguration;
import com.example.brickscrapper.config.SeleniumConfiguration;
import com.example.brickscrapper.constant.URLString;
import com.example.brickscrapper.constant.tokopedia.TokopediaElementXPath;
import com.example.brickscrapper.constant.tokopedia.TokopediaQueryParam;
import com.example.brickscrapper.model.TokopediaProduct;
import com.example.brickscrapper.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TokopediaScrapperService implements ScrapperService<TokopediaProduct> {
    private String urlPath;

    private ChromeDriverFactory driverFactory;

    private WebDriver webDriver;

    private final SeleniumConfiguration seleniumConfiguration;

    private final ScrapperConfiguration scrapperConfiguration;

    @Autowired
    public TokopediaScrapperService(SeleniumConfiguration seleniumConfiguration, ScrapperConfiguration scrapperConfiguration) {
        this.urlPath = scrapperConfiguration.getTokopedia().getUrlPath();

        this.driverFactory = ChromeDriverFactory.getInstance(seleniumConfiguration);
        this.seleniumConfiguration = seleniumConfiguration;
        this.scrapperConfiguration = scrapperConfiguration;
        this.webDriver = driverFactory.getChromeDriver();
    }

    @Override
    public List<TokopediaProduct> startScrapping() {
        List<TokopediaProduct> products = new ArrayList<>();

        Map<String, String> queryParams = new HashMap<>();

        int i = 1;
        while(products.size() < scrapperConfiguration.getTokopedia().getProduct().getCount()) {

            // query param to navigate to each page number
            // each page has maximum of 60 products to show
            queryParams.put(TokopediaQueryParam.PAGING_QUERY_NAME.getVal(), String.valueOf(i));

            // query param to set product sorting
            queryParams.put(TokopediaQueryParam.SORTING_QUERY_NAME.getVal(),
                    scrapperConfiguration.getTokopedia().getProduct().getSortBy().getVal());

            String queryParamString = URLUtil.toQueryParamString(queryParams);
            String url = String.format("%s/%s?%s", URLString.Tokopedia.BASE_URL.getVal(), urlPath, queryParamString);

            List<WebElement> webElements = scrapProductList(url);

            for (WebElement webElement : webElements) {
                TokopediaProduct tProduct = new TokopediaProduct();

                WebElement productLinkE = webElement.findElement(By.xpath(TokopediaElementXPath.PRODUCT_LINK.getQuery()));
                String productLink = productLinkE.getAttribute("href");

                if (productLink.contains(URLString.Tokopedia.ADS_URL.getVal())) {
                    continue;
                }

                tProduct.setLink(productLink);

                products.add(tProduct);

                if (products.size() == scrapperConfiguration.getTokopedia().getProduct().getCount()) {
                    break;
                }
            }

            i++;
        }

        processProductList(products);

        return products;
    }

    private List<WebElement> scrapProductList(String url) {
        log.info("Start scrapping for product list: {}", url);

        webDriver.get(url);

        driverFactory.scrollWindowToBottom("Scrapping");

        return webDriver.findElements(By.xpath(TokopediaElementXPath.PRODUCT_LIST.getQuery()));
    }

    private void processProductList(List<TokopediaProduct> products) {
        for (int i=0; i<products.size(); i++) {
            webDriver.get(products.get(i).getLink());

            //System.out.println(webDriver.findElement(By.xpath(TokopediaElementXPath.ROOT.getQuery())).getAttribute("outerHTML"));
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);


            // wait for the presence of product page container
            webDriverWait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(TokopediaElementXPath.PRODUCT_PAGE_CONTAINER.getQuery())));

            driverFactory.scrollWindowToBottom(String.format("Extracting[%d/%d]", i+1, products.size()));

            // wait for all loader disappear
            webDriverWait.until(ExpectedConditions.
                    invisibilityOfAllElements(webDriver.findElements(By.xpath(TokopediaElementXPath.LOADER.getQuery()))));

            WebElement productPage = webDriver.findElement(By.xpath(TokopediaElementXPath.PRODUCT_PAGE_CONTAINER.getQuery()));
            //System.out.println(productPage.getAttribute("outerHTML"));

            WebElement productImageSlider = productPage.findElement(By.xpath(TokopediaElementXPath.PRODUCT_IMAGE_SLIDER.getQuery()));
            //System.out.println(productImageSlider.getAttribute("outerHTML"));

            List<WebElement> productImageThumbnails = productImageSlider.findElements(By.xpath(TokopediaElementXPath.PRODUCT_IMAGE_THUMBNAIL.getQuery()));

            List<String> productImageLinks = new ArrayList<>();

            for (WebElement thumbnail : productImageThumbnails) {
                String productImageLink = thumbnail.getAttribute("src");

                // thumbnail link uses 100pixel image, so need change it to bigger resolution.
                productImageLink = productImageLink.replace("100-square", "500-square");

                productImageLinks.add(productImageLink);
            }

            WebElement productContent = productPage.findElement(By.xpath(TokopediaElementXPath.PRODUCT_CONTENT.getQuery()));

            String productName = productContent.findElement(By.xpath(TokopediaElementXPath.PRODUCT_NAME.getQuery()))
                    .getText();

            String productRating = productContent.findElement(By.xpath(TokopediaElementXPath.PRODUCT_RATING.getQuery()))
                    .getText();
            //System.out.println(productRating);

            String productPrice = productContent.findElement(By.xpath(TokopediaElementXPath.PRODUCT_PRICE.getQuery()))
                    .getText().replaceAll("\\D+","").concat(".00");
            //System.out.println(productPrice);

            String productDescription = productContent.findElement(By.xpath(TokopediaElementXPath.PRODUCT_DESCRIPTION.getQuery()))
                    .getText();
            //System.out.println(productDescription);

            Document productDescE = Jsoup.parse(productDescription);
            Document.OutputSettings outputSettings = new Document.OutputSettings();
            outputSettings.prettyPrint(false);
            productDescE.outputSettings(outputSettings);
            productDescription = Jsoup.parse(productDescE.html()).text();

            String merchantName = productContent.findElement(By.xpath(TokopediaElementXPath.MERCHANT_NAME.getQuery()))
                    .getText();
            //System.out.println(merchantName.getAttribute("outerHTML"));

            //System.out.println("---------------------------------------------------------------");

            //System.out.println("\n******************************************************\n");

            products.get(i).setImageLinks(productImageLinks);
            products.get(i).setName(productName);
            products.get(i).setRating(Float.valueOf(productRating == null || productRating.isEmpty() ? "0" : productRating));
            products.get(i).setPrice(new BigDecimal(productPrice));
            products.get(i).setDescription(productDescription);
            products.get(i).setMerchant(merchantName);
        }
    }


}
