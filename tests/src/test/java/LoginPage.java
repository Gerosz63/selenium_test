import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;



public class LoginPage extends PageBase {
     By loginUserName = By.id("loginUserName");
     By loginPassword = By.id("login-password");
     By loginButton = By.xpath("//input[@type='submit' and @value='Login']");
     By cookieCheckbox = By.name("cookie");
     By errorMessageBox = By.xpath("//div[@id='content']/div[@class='badresult']");

     public LoginPage(WebDriver driver) {
          super(driver);
          this.driver.get(baseUrl + "login.php");
     }

     public void login(String username, String password) {
          WebElement usernameInput = waitAndReturnElement(loginUserName);
          WebElement passwordInput = waitAndReturnElement(loginPassword);
          WebElement loginButtonElement = waitAndReturnElement(loginButton);

          WebElement cookieCheckboxElement = waitAndReturnElement(cookieCheckbox);
          if (!cookieCheckboxElement.isSelected()) {
               cookieCheckboxElement.click();
          }

          usernameInput.sendKeys(username);
          passwordInput.sendKeys(password);
          loginButtonElement.click();
     }
     public MainPage validLogin(String username, String password) {
          login(username, password);
          MainPage mainPage = new MainPage(driver);
          mainPage.waitForPageToLoad();
          return mainPage;
     }
     public LoginPage invalidLogin(String username, String password) {
          login(username, password);
          return this;
     }
     public WebElement getErrorMessageBox() {
          try {
               return waitAndReturnElement(errorMessageBox);
          } catch (Exception e) {
               return null;
          }
     }
}
