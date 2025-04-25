package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TopicPage {
    private final WebDriver driver;
    
    // XPath локаторы
    private static final String TOPIC_TITLE_XPATH = "//div[contains(@class, 'maintitle')]";
    private static final String POST_CONTENT_XPATH = "//div[contains(@class, 'postcolor')]";
    private static final String REPLY_BUTTON_XPATH = "//a[contains(@href, 'act=Post') and contains(@href, 'CODE=02')]";
    private static final String POST_AUTHOR_XPATH = "//span[contains(@class, 'name')]";
    private static final String NAVIGATION_LINKS_XPATH = "//div[contains(@class, 'pagelinkfloat')]//a";
    
    public TopicPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getPostContents() {
        return driver.findElements(By.xpath(POST_CONTENT_XPATH));
    }
} 