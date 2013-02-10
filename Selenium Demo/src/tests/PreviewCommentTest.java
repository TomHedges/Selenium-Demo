package tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class PreviewCommentTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.chrome.driver","C:\\Users\\Tom\\Documents\\Study\\Birkbeck\\MSc - Creative Industries (Computing)\\1 - Autumn 2012\\ISE\\Assignment 4\\chromedriver.exe");
	driver = new ChromeDriver();
	//driver = new HtmlUnitDriver();
	//driver = new FirefoxDriver();
    baseUrl = "http://www.tomhedges.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPreviewComment() throws Exception {
    driver.get(baseUrl + "/blog");
    driver.findElement(By.linkText("a few thoughts on the new site...")).click();
    driver.findElement(By.linkText("Log in")).click();
    driver.findElement(By.id("edit-name")).clear();
    driver.findElement(By.id("edit-name")).sendKeys("Selenium Test");
    driver.findElement(By.id("edit-pass")).clear();
    driver.findElement(By.id("edit-pass")).sendKeys("Selenium123?");
    driver.findElement(By.id("edit-submit")).click();
    driver.findElement(By.id("edit-subject")).clear();
    driver.findElement(By.id("edit-subject")).sendKeys("i am a test subject");
    new Select(driver.findElement(By.id("edit-comment-body-und-0-format--2"))).selectByVisibleText("Full HTML");
    driver.findElement(By.id("edit-comment-body-und-0-value")).clear();
    driver.findElement(By.id("edit-comment-body-und-0-value")).sendKeys("this is a <strong>test</strong> comment");
    driver.findElement(By.id("edit-preview")).click();
    assertEquals("Preview comment | tomhedges.com", driver.getTitle());
    try {
      assertTrue(isElementPresent(By.linkText("i am a test subject")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    // Warning: verifyTextPresent may require manual changes
    try {
      assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*this is a test comment[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.linkText("Log out")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alert.getText();
    } finally {
      acceptNextAlert = true;
    }
  }
}
