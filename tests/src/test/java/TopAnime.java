import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;



public class TopAnime extends PageBase {

     private By topAnimeHeader = By.xpath("//div[@id='content']//div[@class='pb12']//h2");
     private By topAiringAnime = By.xpath("//a[text()='Top Airing']");
     private By topAnime = By.xpath("//h3[contains(@class, 'anime_ranking_h3')]//a");

     public TopAnime(WebDriver driver) {
          super(driver);
          this.driver.get(baseUrl + "/topanime.php");
     }


     public String getTopAnimeHeaderText() {
          WebElement headerElement = this.waitAndReturnElement(topAnimeHeader);
          return headerElement.getText();
     }

     public String getTopAnimeTitle() {
          try {
               WebElement titleElement = this.waitAndReturnElement(topAnime);
               return titleElement.getText();
          } catch (Exception e) {
               return null; 
          }
     }

     public Anime goToTop1Anime() {
          WebElement topAnimeElement = this.waitAndReturnElement(topAnime);
          topAnimeElement.click();
          return new Anime(this.driver);
     }

     public TopAnime gotoTopAiringAnime() {
          WebElement topAiringAnimeElement = this.waitAndReturnElement(topAiringAnime);
          topAiringAnimeElement.click();
          return this;
     }
}
