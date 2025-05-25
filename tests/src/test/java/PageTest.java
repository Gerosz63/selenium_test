
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

import org.openqa.selenium.support.ui.*;
import org.junit.*;

public class PageTest {
     private WebDriver driver;

     protected Cookie consentCookie = new Cookie(
          "euconsent-v2",
          "CQR6ngAQR6ngAAKA1AENBsFsAP_gAEPgABJ4LPtT_G__bWlr-T73aftkeYxP99h77sQxBgbJE-4FzLvW_JwXx2E5NAzatqIKmRIAu3TBIQNlHJDURVCgaogVryDMaEyUoTNKJ6BkiFMRI2NYCFxvm4tjeQCY5vr991c1mB-t7dr83dzyy4hHn3a5_2S1WJCdAYetDfv8bBOT-9IOd_x8v4v4_F7pE2-eS1n_pWvp7D9-Yls_9X299_bbff7Pn_ful_-_X_vf_n37v943n77v__gs8ACYaFRBGWRACESgYQQIAFBWEBFAgCAABIGiAgBMGBTkDABdYSIAQAoABggBAACDAAEAAAkACEQAUAFAgAAgECgADAAgGAgAYGAAMAFgIBAACA6BimBBAIFgAkZkUGmBKAAkEBLZUIJAECCuEIRZ4BBAiJgoAAAQACgAAQHgsBiSQErEggC4gmgAAIAAAogQIEUnZgCCgM2WovBk-jK0wDB8wTNKYBkARBGRkmxCb9ph45CiEAAA.YAAAAAAAAAAA",
          ".myanimelist.net",
          "/",
          java.util.Date.from(java.time.ZonedDateTime.now().plusYears(1).toInstant())
     );
     private Cookie gdprCookie = new Cookie(
          "m_gdpr_mdl_20250327",
          "1",
          "myanimelist.net",
          "/",
          java.util.Date.from(java.time.ZonedDateTime.now().plusYears(1).toInstant())
     );
     private Cookie sessionCookie; //MALHLOGSESSID
     private Cookie sessionCookie2; //MALSESSIONID
     private Cookie sessionCookie3 = new Cookie(
          "is_logged_in",
          "1",
          ".myanimelist.net",
          "/",
          java.util.Date.from(java.time.ZonedDateTime.now().plusYears(1).toInstant())
     ); //is_logged_in

     private String username = "Almafa123";
     private String password = "AlmaKorte123";
     private String baseUrl = "https://myanimelist.net/";

     @Before
     public void setUp() throws MalformedURLException, java.lang.InterruptedException {
          ChromeOptions options = new ChromeOptions();
          //options.addArguments("--headless");
          driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
          this.driver.manage().window().maximize();

          
          // Add consent cookie and gdpr to bypass the cookie consent popup
          this.driver.get(baseUrl);
          this.driver.manage().addCookie(consentCookie);
          this.driver.manage().addCookie(gdprCookie);

          // I also need to add these to the local storage to bypass the cookie consent popup
          ((JavascriptExecutor) driver).executeScript(
               "window.localStorage.setItem(arguments[0], arguments[1]);",
               "_cmpRepromptHash", "CQR6ngAQR6ngAAKA1AENBsFsAP_gAEPgABJ4LPtT_G__bWlr-T73aftkeYxP99h77sQxBgbJE-4FzLvW_JwXx2E5NAzatqIKmRIAu3TBIQNlHJDURVCgaogVryDMaEyUoTNKJ6BkiFMRI2NYCFxvm4tjeQCY5vr991c1mB-t7dr83dzyy4hHn3a5_2S1WJCdAYetDfv8bBOT-9IOd_x8v4v4_F7pE2-eS1n_pWvp7D9-Yls_9X299_bbff7Pn_ful_-_X_vf_n37v943n77v__gs8ACYaFRBGWRACESgYQQIAFBWEBFAgCAABIGiAgBMGBTkDABdYSIAQAoABggBAACDAAEAAAkACEQAUAFAgAAgECgADAAgGAgAYGAAMAFgIBAACA6BimBBAIFgAkZkUGmBKAAkEBLZUIJAECCuEIRZ4BBAiJgoAAAQACgAAQHgsBiSQErEggC4gmgAAIAAAogQIEUnZgCCgM2WovBk-jK0wDB8wTNKYBkARBGRkmxCb9ph45CiEAAA.YAAAAAAAAAAA.1.7jBD0hXopKwZ/wL8pe9ChQ=="
          );
          ((JavascriptExecutor) driver).executeScript(
               "window.localStorage.setItem(arguments[0], arguments[1]);",
               "gbc_consent", "[]"
          );

          this.driver.navigate().refresh();
          new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.id("qc-cmp2-ui")));
     }

     @Ignore
     @Test
     public void testLoginWithInvalidUsername() {
          LoginPage loginPage = new LoginPage(this.driver);
          LoginPage loginPageNew = loginPage.invalidLogin(username + "45", password);
          WebElement errorMessage = loginPageNew.getErrorMessageBox();
          assertTrue("Expected error message to be displayed", errorMessage != null);
          assertEquals("Your username or password is incorrect.", errorMessage.getText());
          assertTrue("Expected login to fail", loginPageNew.isLoggedOut());
     }
     @Ignore
     @Test
     public void testLoginWithInvalidPassword() {
          LoginPage loginPage = new LoginPage(this.driver);
          LoginPage loginPageNew = loginPage.invalidLogin(username, password + "45");
          WebElement errorMessage = loginPageNew.getErrorMessageBox();
          assertTrue("Expected error message to be displayed", errorMessage != null);
          assertEquals("Your username or password is incorrect.", errorMessage.getText());
          assertTrue("Expected login to fail", !loginPageNew.isLoggedOut());
     }

     @Test
     public void testLoginWithValidCredentials() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);
          String actualUsername = mainPage.getUsername();
          assertEquals("Expected username to match", username, actualUsername);
          
          String actualTitle = mainPage.getPageTitle();
          assertEquals("MyAnimeList.net - Panel", actualTitle);
     }

     @Test
     public void testLogout() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);

          String actualUsername = mainPage.getUsername();
          assertEquals("Expected username to match", username, actualUsername);

          String actualTitle = mainPage.getPageTitle();
          assertEquals("MyAnimeList.net - Panel", actualTitle);

          MainPage loggedOutPage = mainPage.logout();
          assertTrue("Expected to be logged out", loggedOutPage.isLoggedOut());
     }

     @Test
     public void testHoverOverAnime() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);
          assertFalse("Expected anime Menu to be hidden", mainPage.isAnimeMenuVisible());
          mainPage.hoverOverAnimeElement();
          assertTrue("Expected anime Menu to be visible", mainPage.isAnimeMenuVisible());
     }

     
     @Test
     public void testTopAnime() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);
          TopAnime topAnimePage = mainPage.goToTopAnime();
          String actualHeader = topAnimePage.getTopAnimeHeaderText();
          assertTrue("Expected top anime header to match", actualHeader.contains("Top Anime Series"));
     }

     @Test
     public void testMainTopAnimesListTitle() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);
          TopAnime topAnimePage = mainPage.goToTopAnime();
          String actualTitle = topAnimePage.getTopAnimeHeaderText();
          assertTrue("Expected top anime header to match", actualTitle.contains("Top Anime Series"));
     }

     @Test
     public void testTopAiringAnimeNameFromDiffPages() {
          LoginPage loginPage = new LoginPage(this.driver);
          MainPage mainPage = loginPage.validLogin(username, password);
          String topAiringAnimeTitleFromPanel = mainPage.getTopAiringAnimeTitle();

          TopAnime topAnimePage = mainPage.goToTopAnime();
          TopAnime topAiringAnimePage = topAnimePage.gotoTopAiringAnime();
          String topAiringAnimeTitleFromTopAiringAnimePage = topAiringAnimePage.getTopAnimeTitle();

          assertEquals(topAiringAnimeTitleFromPanel, topAiringAnimeTitleFromTopAiringAnimePage);
     }


     @After
     public void close() {
          if (this.driver != null) {
               this.driver.quit();
          }
     }

}
