import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Cookie;

class PageBase {
     protected WebDriver driver;
     protected WebDriverWait wait;
     protected Actions actions;

    
     protected String baseUrl = "https://myanimelist.net/";

     private By usernameLocator = By.xpath("//div[contains(@class, 'header-menu-unit')]//a[@class='header-profile-link']");
     private By logoutButtonLocator = By.xpath("//form[@method='post']//a[text()='Logout']");
     private By loginButtonLocator = By.id("malLogin");
     private By animeMenuLocator = By.xpath("//ul[@id='nav']//li[1]");
     private By animeUlMenuLocator = By.xpath("//ul[@id='nav']//li[1]//ul");

     public PageBase(WebDriver driver) {
          this.driver = driver;
          this.wait = new WebDriverWait(driver, 10);
          this.actions = new Actions(driver);
     }

     protected void waitUntilElementIsVisible(By locator) {
          this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
     }

     protected void waitUntilElementIsClickable(By locator) {
          this.wait.until(ExpectedConditions.elementToBeClickable(locator));
     }
     protected void waitUntilElementInvisible(By locator) {
          this.wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
     }

     protected  void waitAndClickElement(By locator) {
          this.waitUntilElementIsClickable(locator);
          WebElement element = this.driver.findElement(locator);
          element.click();
     }

     protected WebElement waitAndReturnElement(By locator) {
          this.waitUntilElementIsVisible(locator);
          return this.driver.findElement(locator);
     }

     protected void hoverOverElement(By locator) {
          WebElement elementToHover = waitAndReturnElement(locator);
          actions.moveToElement(elementToHover).perform();
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
               return null;
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
          WebElement profileMenu = this.waitAndReturnElement(usernameLocator);
          profileMenu.click();
          WebElement logoutButton;
          logoutButton = this.waitAndReturnElement(logoutButtonLocator);
          logoutButton.click();
          return new MainPage(this.driver);
     }

     public void hoverOverAnimeElement() {
          hoverOverElement(animeMenuLocator);
     }
     public boolean isAnimeMenuVisible() {
          try {
               WebElement animeMenu = this.waitAndReturnElement(animeUlMenuLocator);
               return animeMenu.isDisplayed();
          } catch (Exception e) {
               return false;
          }
     }

     public TopAnime goToTopAnime() {
          this.driver.get(baseUrl + "topanime.php");
          return new TopAnime(this.driver);
     }


}
