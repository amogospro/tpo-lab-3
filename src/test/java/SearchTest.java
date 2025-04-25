import pages.HomePage;
import pages.SearchPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest extends WebDriverSetup {
    
    @Test
    @DisplayName("Тест-кейс 9: Выполнение поиска 'машина'")
    public void testPerformSearch1() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        
        SearchPage searchPage = homePage.search("Машина");

        // Ожидаем, что будут результаты поиска
        List<WebElement> searchResults = searchPage.getSearchResults();
        assertFalse(searchResults.isEmpty(), "Результаты поиска отсутствуют");
    }

    @Test
    @DisplayName("Тест-кейс 10: Выполнение поиска без результатов")
    public void testPerformSearch2() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();

        SearchPage searchPage = homePage.search("123131231231312312312312332");

        // Ожидаем, что не будет результатов поиска
        List<WebElement> searchResults = searchPage.getSearchResults();
        assertTrue(searchResults.isEmpty(), "Результаты поиска присутствуют");
    }

} 