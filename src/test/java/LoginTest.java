import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends WebDriverSetup {

    @Test
    @DisplayName("Тест-кейс 6: Отображение формы входа")
    public void testLoginFormDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();

        LoginPage loginPage = homePage.clickLoginButton();

        // Проверка URL
        assertTrue(driver.getCurrentUrl().contains("act/Login"), "URL не содержит параметр 'login'");
    }

    @Test
    @DisplayName("Тест-кейс 7: Попытка входа с неверными данными")
    public void testLoginWithInvalidCredentials() throws InterruptedException {
        LoginPage loginPage;
        do {
            HomePage homePage = new HomePage(driver);
            homePage.openPage();

            loginPage = homePage.clickLoginButton();
            Thread.sleep(5000);
        } while (driver.getCurrentUrl().contains("logout"));

        // Вводим неверные данные
        loginPage.enterUsername("testuser_invalid");
        loginPage.enterPassword("invalidpassword");
        handleCaptcha();
        loginPage.clickLoginButton();

        // ждем
        Thread.sleep(5000);

        // Проверяем, что остались на странице логина
        assertTrue(driver.getCurrentUrl().contains("act=Login"), "Не остались на странице логина");
    }

    @Test
    @DisplayName("Тест-кейс 8: Попытка входа с верными данными")
    public void testLoginFormFields() throws InterruptedException {
        LoginPage loginPage;
        do {
            HomePage homePage = new HomePage(driver);
            homePage.openPage();

            loginPage = homePage.clickLoginButton();
            Thread.sleep(5000);
        } while (driver.getCurrentUrl().contains("logout"));
        // Вводим тестовые данные в поля и проверяем, что они принимаются
        loginPage.enterUsername("TPOuser1");
        loginPage.enterPassword("TPOuser1");
        handleCaptcha();
        loginPage.clickLoginButton();

        // ждем редирект
        Thread.sleep(5000);


        assertFalse(driver.getCurrentUrl().contains("act=Login"), "URL содержит параметр 'act=login'");
    }
} 