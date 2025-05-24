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
import org.openqa.selenium.Cookie;

class PageBase {
     protected WebDriver driver;
     protected WebDriverWait wait;
    
     protected String baseUrl = "https://myanimelist.net/";

     private By usernameLocator = By.xpath("//div[contains(@class, 'header-menu-unit')]//a[@class='header-profile-link']");
     private By logoutButtonLocator = By.xpath("//form[@method='post']//a[text()='Logout']");
     private By loginButtonLocator = By.id("malLogin");

     public PageBase(WebDriver driver) {
          this.driver = driver;
          this.wait = new WebDriverWait(driver, 10);
     }
    
     protected WebElement waitAndReturnElement(By locator) {
          this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
          return this.driver.findElement(locator);
     } 
    
     public String getBodyText() {
          WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
          return bodyElement.getText();
     }
     
     public String getPageTitle() {
          return this.driver.getTitle();
     }

     public String getUsername() {
          try {
               WebElement usernameElement = this.waitAndReturnElement(usernameLocator);
               return usernameElement.getText();
          } catch (Exception e) {
               return null; // or handle the case when the username is not found
          }
     }

     public boolean isLoggedIn() {
          try {
               this.waitAndReturnElement(usernameLocator);
               return true;
          } catch (Exception e) {
               return false;
          }
     }

     public boolean isLoggedOut() {
          try {
               this.waitAndReturnElement(loginButtonLocator);
               return true;
          } catch (Exception e) {
               return false;
          }
     }

     public MainPage logout() {
          WebElement logoutButton;
          try {
               logoutButton = this.waitAndReturnElement(logoutButtonLocator);
          } catch (Exception e) {
               return new MainPage(this.driver);
          }
          logoutButton.click();
          return new MainPage(this.driver);
     }

}
