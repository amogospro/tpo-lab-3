import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebDriverSetup {

    protected WebDriver driver;
    protected static String browser = System.getProperty("browser", "firefox");
    protected final String BASE_URL = "https://www.yaplakal.com";
    private static final Logger LOGGER = Logger.getLogger(WebDriverSetup.class.getName());

    private static final String UNVERIFIED_CAPTCHA_XPATH =
            "//div[@id='recaptcha-accessible-status' and contains(., 'requires verification')]";
    private static final String VERIFIED_CAPTCHA_XPATH =
            "//div[@id='recaptcha-accessible-status' and contains(., 'You are verified')]";

    public boolean isCaptchaPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(UNVERIFIED_CAPTCHA_XPATH)
            )) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void handleCaptcha() {
        if (isCaptchaPresent()) {
            System.out.println("⚠️ CAPTCHA detected! You have 120 seconds to solve it manually...");

            try {
                new WebDriverWait(driver, Duration.ofSeconds(120))
                        .until(d -> {
                            boolean isVerified = !d.findElements(By.xpath(VERIFIED_CAPTCHA_XPATH)).isEmpty();
                            boolean isStillPresent = !d.findElements(By.xpath(UNVERIFIED_CAPTCHA_XPATH)).isEmpty();
                            return isVerified || !isStillPresent;
                        });

                System.out.println("CAPTCHA resolved successfully");
            } catch (TimeoutException e) {
                System.out.println("CAPTCHA resolution timeout!");
                throw new RuntimeException("CAPTCHA not resolved within allowed time");
            }
        }
    }


    @BeforeAll
    public static void setupClass() {
        // Suppress excessive logging
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "WARN");
//        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        try {
            // Set the path to ChromeDriver executable - use the same path as in your original file

            // Let WebDriverManager handle the driver setup
            if ("firefox".equalsIgnoreCase(browser)) {
                WebDriverManager.firefoxdriver().setup();
            } else {
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\wgmlg\\tpo-lab-3\\webdrivers\\chromedriver-win64\\chromedriver.exe");
                WebDriverManager.chromedriver().clearDriverCache().setup();
            }
        } catch (Exception e) {
            LOGGER.severe("Failed to setup WebDriver: " + e.getMessage());
            throw e;
        }
    }

    @BeforeEach
    public void setupTest() {
        try {
            if ("firefox".equalsIgnoreCase(browser)) {
                FirefoxOptions options = new FirefoxOptions();
                // Do not use headless mode for better compatibility
                // options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
                options.addArguments("--private");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);

                driver = new FirefoxDriver(options);
            } else {
                // Chrome is default
                ChromeOptions options = new ChromeOptions();
                 options.addArguments("--headless=new");

                options.addArguments("--incognito");

                options.setExperimentalOption("debuggerAddress", "localhost:9222");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--exclude-switches=enable-automation");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);

                driver = new ChromeDriver(options);
                driver.switchTo().newWindow(WindowType.WINDOW);
                assertEquals("",driver.getTitle());
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize WebDriver: " + e.getMessage());
            throw e;
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                LOGGER.warning("Error quitting WebDriver: " + e.getMessage());
            }
        }
    }
} 