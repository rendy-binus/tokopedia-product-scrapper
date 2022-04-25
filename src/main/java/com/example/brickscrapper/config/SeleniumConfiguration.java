package com.example.brickscrapper.config;

import com.example.brickscrapper.constant.ChromeDriverPath;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.example.brickscrapper.util.OSUtil.*;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "selenium")
@Slf4j
public class SeleniumConfiguration {
    /**
     * Path to Chrome Driver execution or binary file
     * e.g. /usr/bin/chromedriver_100.0.4896.60
     */
    private String chromeDriverPath;

    /**
     * List of Chrome Driver arguments
     */
    private List<String> chromeDriverArgs;

    /**
     * Chrome Driver user agent
     */
    private String chromeDriverUserAgent;

    /**
     * IP address of the domain to be whitelisted on Chrome Driver
     */
    private List<String> chromeDriverWhitelistedIps;

    /**
     * Pixel value for scrolling
     * Decrease this value if the internet speed is slow or the processing capability is slow
     * Default to 500 units
     */
    @Min(1)
    @Max(1000)
    private int scrollingValue = 500;

    /**
     * Interval between each scrolling action
     * increase this value if the internet speed is slow or the processing capability is slow
     * Default to 1500 milliseconds
     */
    @DurationUnit(ChronoUnit.MILLIS)
    @Min(1500)
    @Max(60000)
    private Duration scrollingInterval = Duration.of(1500, ChronoUnit.MILLIS);

    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;

        if (IS_WINDOWS) {
            this.chromeDriverPath += ChromeDriverPath.WINDOWS.getPath();
        }
        else if (IS_OSX) {
            this.chromeDriverPath += ChromeDriverPath.MAC64.getPath();
        }
        else if (IS_OSX_ARM) {
            this.chromeDriverPath += ChromeDriverPath.MAC64_M1.getPath();
        }
        else if (IS_AIX) {
            this.chromeDriverPath += ChromeDriverPath.UNIX.getPath();
        }
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        if (chromeDriverUserAgent != null) {
            chromeDriverUserAgent = "--user-agent=" + chromeDriverUserAgent;
            chromeDriverArgs.add(chromeDriverUserAgent);
        }

        System.setProperty("webdriver.chrome.whitelistedIps", String.join(",", chromeDriverWhitelistedIps));
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        chromeOptions.addArguments(chromeDriverArgs);

        //chromeOptions.setBinary(chromeDriverPath);

        return chromeOptions;
    }


}
