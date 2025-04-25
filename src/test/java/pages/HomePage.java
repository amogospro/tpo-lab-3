package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class HomePage {
    private final WebDriver driver;
    
    // XPath локаторы
    private static final String LOGIN_BUTTON_XPATH = "//a[contains(@href, 'act/Login')]";
    private static final String SEARCH_BUTTON_XPATH = "//a[contains(@onclick, 'Поиск…')]";
    private static final String SEARCH_INPUT_XPATH = "//input[@value='Поиск…']";
    private static final String FORUM_SECTION_XPATH = "//div[contains(@class, 'forumDescr')]//a[contains(@href, 'forum')]";
    private static final String MAIN_MENU_XPATH = "//a[@title='На главную']";
    private static final String REGISTER_LINK_XPATH = "//a[contains(@href, 'act/Reg/CODE/00')]";
    private static final String POST_XPATH = "//td[contains(@class, 'newshead')]";
    private static final String COMMENTS_XPATH = "//a[contains(., 'Комментарии')]";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void openPage() {
        driver.get("https://www.yaplakal.com/");
    }

    public LoginPage clickLoginButton() {
        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        loginButton.click();
        return new LoginPage(driver);
    }

    public SearchPage search(String search) {
        WebElement searchFiled = driver.findElement(By.xpath(SEARCH_INPUT_XPATH));
        searchFiled.clear();
        searchFiled.sendKeys(search);

        WebElement searchButton = driver.findElement(By.xpath(SEARCH_BUTTON_XPATH));
        searchButton.click();
        return new SearchPage(driver);
    }
    public List<WebElement> getForumSections() {
        return driver.findElements(By.xpath(FORUM_SECTION_XPATH));
    }
    
    public boolean isMainMenuDisplayed() {
        return !driver.findElements(By.xpath(MAIN_MENU_XPATH)).isEmpty();
    }
    
    public void clickRegisterLink() {
        WebElement registerLink = driver.findElement(By.xpath(REGISTER_LINK_XPATH));
        registerLink.click();
    }

    public TopicPage clickCommentsLink() {
        WebElement registerLink = driver.findElements(By.xpath(COMMENTS_XPATH)).get(3);
        registerLink.click();
        return new TopicPage(driver);
    }
    
    public int postCount() {
        return driver.findElements(By.xpath(POST_XPATH)).size();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
} 