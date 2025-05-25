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



public class MainPage extends PageBase {

     By topAiringAnimeTitleLocator = By.xpath("//div[@id='content']//div[@class='right-column']//div[contains(@class, 'airing_ranking')]//ul//li[1]//div//h3//a");

     public MainPage(WebDriver driver) {
          super(driver);
          
     }
     public void waitForPageToLoad() {
            this.waitUntilElementIsVisible(topAiringAnimeTitleLocator);
     }
     public String getTopAiringAnimeTitle() {
          try {
               WebElement titleElement = this.waitAndReturnElement(topAiringAnimeTitleLocator);
               return titleElement.getText();
          } catch (Exception e) {
               return null;
          }
     }
}
