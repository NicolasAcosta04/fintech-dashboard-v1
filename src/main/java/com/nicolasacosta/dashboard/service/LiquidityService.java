package com.nicolasacosta.dashboard.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class LiquidityService {

    public int getLatestGlobalM2() throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://en.macromicro.me/charts/3439/major-bank-m2-comparsion");
        log.info("Fetching Global M2 data from MacroMicro...");
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(By.cssSelector("span.val"), "0.0000")
        ));
        WebElement gaugeValue = driver.findElement(By.cssSelector("span.val"));
        log.info("Number for Global m2: {}", gaugeValue.getText());
        int globalM2 = Integer.parseInt(gaugeValue.getText().replaceAll(",", ""));
        driver.quit();
        return globalM2;
    }

}
