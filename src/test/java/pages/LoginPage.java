package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver driver;
    
    // XPath локаторы
    private static final String USERNAME_FIELD_XPATH = "//input[@name='UserName']";
    private static final String PASSWORD_FIELD_XPATH = "//input[@name='PassWord']";
    private static final String LOGIN_BUTTON_XPATH = "//div[@class='pformstrip'][.//input[@name='submit']]";
    private static final String ERROR_MESSAGE_XPATH = "//div[contains(., 'Неверный пароль')]";
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void enterUsername(String username) {
        WebElement usernameField = driver.findElement(By.xpath(USERNAME_FIELD_XPATH));
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        WebElement passwordField = driver.findElement(By.xpath(PASSWORD_FIELD_XPATH));
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    public HomePage clickLoginButton() {
        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        loginButton.click();
        return new HomePage(driver);
    }
    
    public boolean isErrorMessageDisplayed() {
        return !driver.findElements(By.xpath(ERROR_MESSAGE_XPATH)).isEmpty();
    }
    
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return driver.findElement(By.xpath(ERROR_MESSAGE_XPATH)).getText();
        }
        return "";
    }
    
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }
} 