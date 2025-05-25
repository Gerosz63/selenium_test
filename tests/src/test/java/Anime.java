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



public class Anime extends PageBase {

     private By episodesNumberLocator = By.xpath("//div[@class='spaceit_pad'][./span[contains(text(), 'Episodes:')]]");
     private By episodesLinkLocator = By.xpath("//div[@id='horiznav_nav']//a[text()='Episodes']");
     private By addListLocator = By.xpath("//a[text()='Add to List']");
     private By detailEditorLinkLocator = By.xpath("//a[text()='Edit Details']");
     private By scoreLocator = By.id("myinfo_score");
     private By statusLocator = By.id("myinfo_status");
     private By watchedEpisodeNumberLocator = By.id("myinfo_watchedeps");



     public Anime(WebDriver driver) {
          super(driver);
          waitUntilElementIsVisible(episodesNumberLocator);
     }
     public String getEpisodesNumber() {
          try {
               WebElement episodesElement = this.waitAndReturnElement(episodesNumberLocator);
               return episodesElement.getText();
          } catch (NoSuchElementException e) {
               return null; // or handle the exception as needed
          }
     }
     public AnimeEpisodes goToEpisodes() {
          WebElement episodesLink = this.waitAndReturnElement(episodesLinkLocator);
          episodesLink.click();
          return new AnimeEpisodes(this.driver);
     }

     public void addToList() {
          this.waitAndClickElement(addListLocator);
          waitUntilElementIsVisible(detailEditorLinkLocator);
     }


     public String getAnimeStatus() {
          try {
               WebElement statusElement = this.waitAndReturnElement(statusLocator);
               return statusElement.getAttribute("value");
          } catch (NoSuchElementException e) {
               return null; // or handle the exception as needed
          }
     }

     public String getAnimeScore() {
          try {
               WebElement scoreElement = this.waitAndReturnElement(scoreLocator);
               return scoreElement.getAttribute("value");
          } catch (NoSuchElementException e) {
               return null;
          }
     }

     public String getAnimeWatchedEpisodes() {
          try {
               WebElement watchedElement = this.waitAndReturnElement(watchedEpisodeNumberLocator);
               return watchedElement.getAttribute("value");
          } catch (NoSuchElementException e) {
               return null;
          }
     }

     public AnimeStatus gotoAnimeStatus() {
          this.waitAndClickElement(detailEditorLinkLocator);
          return new AnimeStatus(this.driver);
     }

     public boolean isAddlistElementVisible() {
          try {
               WebElement element = this.waitAndReturnElement(addListLocator);
               return element.isDisplayed();
          } catch (Exception e) {
               return false;
          }
     }
}
