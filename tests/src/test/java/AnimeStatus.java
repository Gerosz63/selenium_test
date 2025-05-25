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
import org.openqa.selenium.support.ui.Select;


public class AnimeStatus extends PageBase {

     private By statusSetLocator = By.id("add_anime_status");
     private By episodeSetLocator = By.id("add_anime_num_watched_episodes");
     private By scoreSetLocator = By.id("add_anime_score");
     private By submitSetLocator = By.xpath("//table[@id='dialog']//div[3]//input[@type='button' and @value='Submit']");
     private By deleteSetLocator = By.xpath("//table[@id='dialog']//div[3]//input[@type='button' and @value='Delete']");
     private By goToAnimeLocator = By.xpath("//a[text()='Go to Anime Page']");

     public AnimeStatus(WebDriver driver) {
          super(driver);
     }


     public Anime setStatus(String status, String episodes, String score) {
          waitUntilElementIsVisible(deleteSetLocator);
          Select statusSelect = new Select(this.waitAndReturnElement(statusSetLocator));
          statusSelect.selectByValue(status);

          WebElement episodeElement = this.waitAndReturnElement(episodeSetLocator);
          episodeElement.clear();
          episodeElement.sendKeys(episodes);

          Select scoreSelect = new Select(this.waitAndReturnElement(scoreSetLocator));
          scoreSelect.selectByValue(score);

          this.waitAndClickElement(submitSetLocator);
          this.waitAndClickElement(goToAnimeLocator);
          return new Anime(this.driver);
     }

     public Anime deleteStatus() {
          this.waitAndClickElement(deleteSetLocator);
          this.driver.switchTo().alert().accept();
          this.waitAndClickElement(goToAnimeLocator);
          return new Anime(this.driver);
     }



}