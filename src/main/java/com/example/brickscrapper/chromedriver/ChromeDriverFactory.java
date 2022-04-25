package com.example.brickscrapper.chromedriver;

import com.example.brickscrapper.config.SeleniumConfiguration;
import me.tongfei.progressbar.ProgressBar;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverFactory {

    private static ChromeDriverFactory factory = null;
    private final ChromeDriver chromeDriver;
    private final JavascriptExecutor javascriptExecutor;
    private final long scrollingValue;
    private final long scrollingInterval;

    private ChromeDriverFactory(SeleniumConfiguration seleniumConfiguration) {
        this.chromeDriver = new ChromeDriver(seleniumConfiguration.getChromeOptions());
        this.javascriptExecutor = chromeDriver;
        this.scrollingValue = seleniumConfiguration.getScrollingValue();
        this.scrollingInterval = seleniumConfiguration.getScrollingInterval().toMillis();
    }

    public static ChromeDriverFactory getInstance(SeleniumConfiguration seleniumConfiguration) {
        if (factory == null) {
            factory = new ChromeDriverFactory(seleniumConfiguration);
        }

        return factory;
    }

    public ChromeDriver getChromeDriver() {
        return chromeDriver;
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return javascriptExecutor;
    }

    public void scrollWindowToBottom(String progressBarName) {
        progressBarName = progressBarName == null ? "..." : progressBarName;

        scrollWindowToBottom(progressBarName, scrollingValue);
    }

    public void scrollWindowToBottom(String progressBarName, long scrollingValue) {
        progressBarName = progressBarName == null ? "..." : progressBarName;
        try(ProgressBar progressBar = new ProgressBar(progressBarName,
                (long) javascriptExecutor.executeScript("return document.body.scrollHeight"))) {

            long currentScroll = 0;

            while (true) {
                Thread.sleep(scrollingInterval);

                javascriptExecutor.executeScript(String.format("window.scrollBy(0,%d)",scrollingValue));

                long newScrollHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight");

                progressBar.maxHint(newScrollHeight);

                currentScroll += scrollingValue;

                if (currentScroll > newScrollHeight) {
                    currentScroll = newScrollHeight;
                }

                progressBar.stepTo(currentScroll);

                if (newScrollHeight == currentScroll) {
                    break;
                }
            }

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
