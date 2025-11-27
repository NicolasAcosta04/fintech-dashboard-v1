package com.nicolasacosta.dashboard.service;

import com.nicolasacosta.dashboard.data.FearAndGreedData;
import com.nicolasacosta.dashboard.enums.FearAndGreedLevels;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
public class SentimentService {

    public FearAndGreedData getCnnFearAndGreedData() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://edition.cnn.com/markets/fear-and-greed");
        log.info("Fetching CNN Fear and Greed Index data");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement gaugeValue = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("market-fng-gauge__dial-number-value"))
        );
        log.info("Number for CNN fng: {}", gaugeValue.getText());
        int number = Integer.parseInt(gaugeValue.getText());
        FearAndGreedLevels level = mapLevel(number);
        driver.quit();
        return new FearAndGreedData(number, level);
    }

    public FearAndGreedData getCryptoFearAndGreedData() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://alternative.me/crypto/fear-and-greed-index/");
        log.info("Fetching Crypto Fear and Greed Index data");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        WebElement gaugeValue = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("fng-circle"))
        );
        log.info("Number for crypto fng: {}", gaugeValue.getText());
        int number = Integer.parseInt(gaugeValue.getText());
        FearAndGreedLevels level = mapLevel(number);
        driver.quit();
        return new FearAndGreedData(number, level);
    }

    private FearAndGreedLevels mapLevel(int number) {
        if (number >= 0 && number <= 24) {
            return FearAndGreedLevels.EXTREME_FEAR;
        } else if (number >= 25 && number <= 44) {
            return FearAndGreedLevels.FEAR;
        } else if (number >= 45 && number <= 55) {
            return FearAndGreedLevels.NEUTRAL;
        } else if (number >= 56 && number <= 75) {
            return FearAndGreedLevels.GREED;
        } else if (number >= 76 && number <= 100) {
            return FearAndGreedLevels.EXTREME_GREED;
        } else {
            throw new IllegalArgumentException("Number out of range: " + number);
        }
    }
}
