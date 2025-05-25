import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;



public class AnimeEpisodes extends PageBase {

     private By episodeListLocator = By.xpath("//table//table[contains(@class,'episode_list')]/tbody");

     public AnimeEpisodes(WebDriver driver) {
          super(driver);
     }

     public int getEpisodeCount() {
          WebElement tbody = driver.findElement(episodeListLocator);
          return tbody.findElements(By.tagName("tr")).size();
     }

}
