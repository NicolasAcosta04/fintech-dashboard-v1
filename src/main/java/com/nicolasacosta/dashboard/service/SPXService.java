package com.nicolasacosta.dashboard.service;

import com.nicolasacosta.dashboard.data.SPXData;
import com.nicolasacosta.dashboard.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@Component
public class SPXService {

    private volatile double currentPrice = 6715.79;
//    private volatile double averagePrice = 6086.79;

//    public static void generateTick() {
//        long start = System.nanoTime();
//        WebDriverManager.chromedriver().setup();
//
//        WebDriver driver = getWebDriver();
//
//        try {
//            driver.get("https://www.tradingview.com/chart/?symbol=SP%3ASPX");
//
//            log.info("Fetching SPX price from TradingView...");
//
//            // Wait for page to load if necessary (use WebDriverWait for dynamic content)
//
//            // Get the title of the page
//            String title = driver.getTitle();
//            log.info("Page Title: {}", title);
//        } finally {
//            driver.quit();
//        }
//
//        long elapsedMs = NANOSECONDS.toMillis(System.nanoTime() - start);
//        log.info("Elapsed time: {} ms", elapsedMs);
//    }
//
//    private static WebDriver getWebDriver() {
//        ChromeOptions options = new ChromeOptions();
//        // Turn off images
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("profile.managed_default_content_settings.images", 2);
//
//        // Optionally block CSS
//        prefs.put("profile.managed_default_content_settings.stylesheets", 2);
//
//        // You can add other content types too: fonts, javascript can be blocked but may break page
////        prefs.put("profile.managed_default_content_settings.fonts", 2);
////        prefs.put("profile.managed_default_content_settings.javascript", 2);
//
//        options.setExperimentalOption("prefs", prefs);
//        options.addArguments("--headless"); // Run Chrome in headless mode
//        options.addArguments("--disable-gpu"); // Recommended for headless on Windows
//
//
//        return new ChromeDriver(options);
//    }
//
//    public static void main(String[] args) {
//        generateTick();
//    }

    public SPXData generateTick() {
        // Simulate price changes with some randomness
        double volatility = getRandomVolatility();
        double newPrice = getNewPrice(volatility);
        double change = newPrice - currentPrice;
        var changePercent = (change / currentPrice) * 100.0;
        int tickDirection = change > 0 ? 1 : (change < 0 ? -1 : 0);

        // Update current price for next tick
        currentPrice = newPrice;

        return new SPXData(
                "SPX",
                "S&P 500",
                Math.round(newPrice * 100.0) / 100.0, // Round to 2 decimal places
                Math.round(change * 100.0) / 100.0,
                Math.round(changePercent * 100.0) / 100.0,
                System.currentTimeMillis(),
                Status.OPEN,
                tickDirection
        );
    }

    private double getRandomVolatility() {
        return 0.025 + Math.random() * 0.02; // 2.5% to 4.5% daily volatility
    }

    private double getNewPrice(double volatility) {
        Random random = new Random();
        double dt = 1.0 / (252 * 24); // 1 second interval in a year
//        double mu = -0.0001 * (currentPrice - averagePrice); // Mean reversion drift
        double mu = 0.0002 * Math.sin(System.currentTimeMillis() / 60000.0); // Variable drift based on time
        double Z = random.nextGaussian();
        return currentPrice * (Math.exp((mu - 0.5 * volatility * volatility) * dt + volatility * Math.sqrt(dt) * Z));
    }
}
