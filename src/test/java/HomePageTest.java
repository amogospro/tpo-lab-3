import org.openqa.selenium.interactions.Actions;
import pages.HomePage;
import pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.TopicPage;


import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends WebDriverSetup {

    @Test
    @DisplayName("Тест-кейс 1: Проверка загрузки главной страницы")
    public void testHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        
        assertTrue(homePage.isMainMenuDisplayed(), "Главное меню не отображается");
        assertNotNull(homePage.getPageTitle(), "Заголовок страницы отсутствует");
        assertTrue(homePage.getPageTitle().contains("ЯПлакалъ"), "Заголовок страницы не содержит 'ЯПлакалъ'");
    }
    
    @Test
    @DisplayName("Тест-кейс 2: Проверка доступности ссылки на авторизацию")
    public void testLoginLinkAvailable() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        
        LoginPage loginPage = homePage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("act/Login"), "URL не содержит параметр 'act=login'");
    }

    @Test
    @DisplayName("Тест-кейс 3: Проверка перехода на страницу регистрации")
    public void testRegistrationLinkAvailable() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        
        homePage.clickRegisterLink();
        assertTrue(driver.getCurrentUrl().contains("act/Reg"), "URL не содержит параметр 'act=Reg'");
    }

    @Test
    @DisplayName("Тест-кейс 4: проверка бесконечного скролла")
    public void testInfiniteScroll() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();

        assertTrue(homePage.isMainMenuDisplayed(), "Главное меню не отображается");
        assertNotNull(homePage.getPageTitle(), "Заголовок страницы отсутствует");

        int oldCount = homePage.postCount();

        Thread.sleep(10000);


        new Actions(driver)
                .scrollByAmount(0, 100000)
                .perform();

        Thread.sleep(10000);

        int newCount = homePage.postCount();
        assertNotEquals(oldCount, newCount, "Количество постов не изменилось" + newCount);
    }

    @Test
    @DisplayName("Тест-кейс 5: Проверка чтения комментариев")
    public void testComments() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        Thread.sleep(5000);

        TopicPage topicPage = homePage.clickCommentsLink();
        Thread.sleep(5000);

        int commentsCount = topicPage.getPostContents().size();
        assertNotEquals(0, commentsCount, "Найдено 0 комментариев");
    }
} 