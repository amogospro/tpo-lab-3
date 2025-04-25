package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage {
    private final WebDriver driver;
    
    // XPath локаторы
    private static final String SEARCH_FIELD_XPATH = "//input[@name='keywords']";
    private static final String SEARCH_BUTTON_XPATH = "//input[@type='submit']";
    private static final String SEARCH_RESULTS_XPATH = "//tr[.//a[contains(@class, 'subtitle')]]";
    private static final String FORUM_DROPDOWN_XPATH = "//select[@name='forums[]']";
    private static final String SEARCH_OPTIONS_XPATH = "//select[@name='namesearch']";
    
    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void enterSearchQuery(String query) {
        WebElement searchField = driver.findElement(By.xpath(SEARCH_FIELD_XPATH));
        searchField.clear();
        searchField.sendKeys(query);
    }
    
    public void clickSearchButton() {
        WebElement searchButton = driver.findElement(By.xpath(SEARCH_BUTTON_XPATH));
        searchButton.click();
    }
    
    public List<WebElement> getSearchResults() {
        return driver.findElements(By.xpath(SEARCH_RESULTS_XPATH));
    }
    
    public boolean areSearchResultsDisplayed() {
        return !getSearchResults().isEmpty();
    }
    
    public TopicPage clickOnSearchResultByIndex(int index) {
        List<WebElement> results = getSearchResults();
        if (index >= 0 && index < results.size()) {
            results.get(index).click();
            return new TopicPage(driver);
        }
        throw new IndexOutOfBoundsException("Search result index out of bounds");
    }
    
    public void selectForumInDropdown(String forumName) {
        WebElement dropdown = driver.findElement(By.xpath(FORUM_DROPDOWN_XPATH));
        dropdown.sendKeys(forumName);
    }
    
    public void selectSearchOption(String option) {
        WebElement optionsDropdown = driver.findElement(By.xpath(SEARCH_OPTIONS_XPATH));
        optionsDropdown.sendKeys(option);
    }
    
    public SearchPage performSearch(String query) {
        enterSearchQuery(query);
        clickSearchButton();
        return this;
    }
} 