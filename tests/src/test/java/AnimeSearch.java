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



public class AnimeSearch extends PageBase {

     private By searchBoxLocator = By.id("q");
     private By searchButtonLocator = By.xpath("//button[@type='submit' and contains(@class, 'inputButton')]");
     private By searchResultTableLocator = By.xpath("//table[.//td[text()='Title']]/tbody/tr[last()]");

     public AnimeSearch(WebDriver driver) {
          super(driver);
          this.driver.get(baseUrl + "/anime.php");
     }

     public void searchAnime(String animeName) {
          WebElement searchBox = this.waitAndReturnElement(searchBoxLocator);
          searchBox.clear();
          searchBox.sendKeys(animeName);

          WebElement searchButton = this.waitAndReturnElement(searchButtonLocator);
          searchButton.click();
          waitUntilElementIsVisible(searchResultTableLocator);
     }

     public boolean isSearchResultPresent(String animeName) {
          try {
               this.waitAndReturnElement(By.xpath("//strong[text()='" + animeName + "']"));
               return true;
          } catch (Exception e) {
               return false;
          }
     }

     public String getSearchResultType(String animeName) {
          try {
               WebElement typeElement = this.waitAndReturnElement(By.xpath("//tr[.//strong[text()='" + animeName + "']]//td[3]"));
               return typeElement.getText();
          } catch (NoSuchElementException e) {
               return null;
          }
     }

     public String getSearchResultEpisodes(String animeName) {
          try {
               WebElement epsElement = this.waitAndReturnElement(By.xpath("//tr[.//strong[text()='" + animeName + "']]//td[4]"));
               return epsElement.getText();
          } catch (NoSuchElementException e) {
               return null;
          }
     }

}
