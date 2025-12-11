package utils;

  import org.apache.commons.io.FileUtils;
  import org.openqa.selenium.*;
  import org.openqa.selenium.NoSuchElementException;
  import org.openqa.selenium.interactions.Actions;
  import org.openqa.selenium.support.ui.*;

  import java.io.File;
  import java.io.IOException;
  import java.text.SimpleDateFormat;
  import java.time.Duration;
  import java.util.*;


  public class Utils {
      private static final long DEFAULT_TIMEOUT = 15;
      private static final long DEFAULT_POLLING = 1;
      private static final long IMPLICIT_TIME = 5;

      private WebDriver driver;
      private Actions action;
      private JavascriptExecutor js;

      public Utils(WebDriver driver) {
          this.driver = driver;
          this.action = new Actions(driver);
          this.js = (JavascriptExecutor) driver;
      }

      // ---------- Fluent Wait Core ----------
      public WebElement fluentWaitForElement(By locator, long timeout, long polling) {
          Wait<WebDriver> wait = new FluentWait<>(driver)
                  .withTimeout(Duration.ofSeconds(timeout))
                  .pollingEvery(Duration.ofSeconds(polling))
                  .ignoring(NoSuchElementException.class)
                  .ignoring(StaleElementReferenceException.class)
                  .ignoring(ElementClickInterceptedException.class);
          return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      }

      public List<WebElement> fluentWaitForElements(By locator, long timeout, long polling) {
          Wait<WebDriver> wait = new FluentWait<>(driver)
                  .withTimeout(Duration.ofSeconds(timeout))
                  .pollingEvery(Duration.ofSeconds(polling))
                  .ignoring(NoSuchElementException.class)
                  .ignoring(StaleElementReferenceException.class);
          return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
      }

      public WebElement getWebElement(By locator) {
          return fluentWaitForElement(locator, DEFAULT_TIMEOUT, DEFAULT_POLLING);
      }

      public List<WebElement> getMatchingWebElements(By locator) {
          try {
              return fluentWaitForElements(locator, DEFAULT_TIMEOUT, DEFAULT_POLLING);
          } catch (Exception e) {
              return new ArrayList<>();
          }
      }

      public List<WebElement> findElements(By locator) {
          return getMatchingWebElements(locator);
      }

      // ---------- Element Interaction ----------
      public void clickElement(By locator) {
          getWebElement(locator).click();
      }

      public void clickAndWaitElement(By locator, int timer) {
          getWebElement(locator).click();
      }

      public void doubleClick(By locator) {
          action.doubleClick(getWebElement(locator)).perform();
      }

      public void jsClickElement(By locator) {
          js.executeScript("arguments[0].click();", getWebElement(locator));
      }

      public void inputText(By locator, String sText) {
          getWebElement(locator).sendKeys(sText);
      }

      public void clearAndInputText(By locator, String sText) {
          WebElement ele = getWebElement(locator);
          ele.clear();
          ele.sendKeys(sText);
      }

      public void clearInput(By locator, String sText) {
          getWebElement(locator).clear();
      }

      public void scrollToElement(By locator) {
          js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
      }

      public void hoverOverElement(By locator) {
          action.moveToElement(getWebElement(locator)).perform();
      }

      public String getText(By locator) {
          try {
              return getWebElement(locator).getText();
          } catch (Exception e) {
              return null;
          }
      }

      public String getAttribute(By locator, String sAttrKey) {
          return getWebElement(locator).getAttribute(sAttrKey);
      }

      public boolean isElementPresent(By locator) {
          try {
              getWebElement(locator);
              return true;
          } catch (Exception e) {
              return false;
          }
      }

      public boolean isAttributePresent(By locator, String sAttrKey) {
          try {
              return getWebElement(locator).getAttribute(sAttrKey) != null;
          } catch (Exception e) {
              return false;
          }
      }

      public boolean isElementVisible(By locator, long iTimeInSeconds) {
          try {
              waitUntilElementIsVisible(locator, iTimeInSeconds);
              return true;
          } catch (Exception e) {
              return false;
          }
      }

      public boolean isElementVisible(By locator) {
          return isElementVisible(locator, DEFAULT_TIMEOUT);
      }

      public boolean isAlertVisible() {
          try {
              waitForAlert(DEFAULT_TIMEOUT);
              return true;
          } catch (NoAlertPresentException e) {
              return false;
          }
      }

      // ---------- Waits ----------
      public void waitUntilElementIsVisible(By locator, long iTimeout) {
          new WebDriverWait(driver, Duration.ofSeconds(iTimeout))
                  .until(ExpectedConditions.visibilityOfElementLocated(locator));
      }

      public void waitUntilElementIsInVisible(By locator, long iTimeout) {
          new WebDriverWait(driver, Duration.ofSeconds(iTimeout))
                  .until(ExpectedConditions.invisibilityOfElementLocated(locator));
      }

      public void waitForAlert(long iTimeout) {
          new WebDriverWait(driver, Duration.ofSeconds(iTimeout))
                  .until(ExpectedConditions.alertIsPresent());
      }

      public void waitUntilElementIsClickable(By locator, long iTimeout) {
          new WebDriverWait(driver, Duration.ofSeconds(iTimeout))
                  .until(ExpectedConditions.elementToBeClickable(locator));
      }

      public void waitTillClickableAndClick(By locator, long iTimeout) {
          for (int i = 0; i < iTimeout; i++) {
              try {
                  clickElement(locator);
                  break;
              } catch (Exception ignored) {}
          }
      }

      public void waitForFrameAndSwitch(By locator, long iTimeout) {
          new WebDriverWait(driver, Duration.ofSeconds(iTimeout))
                  .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
      }

      // ---------- Frame & Window ----------
      public void switchToFrame(By locator) {
          driver.switchTo().frame(getWebElement(locator));
      }

      public void switchToDefault() {
          driver.switchTo().defaultContent();
      }

      public void openNewWindowWithURL(String sURL) {
          js.executeScript("window.open()");
          ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
          driver.switchTo().window(tabs.get(1));
          driver.get(sURL);
      }

      public void closeCurrentWindow() {
          ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
          driver.close();
          if (!tabs.isEmpty()) {
              driver.switchTo().window(tabs.get(0));
          }
      }

      public void switchToWindow(String windowHandle) {
          driver.switchTo().window(windowHandle);
      }

      public void switchToNewWindow() {
          String mainWindow = driver.getWindowHandle();
          Set<String> handles = driver.getWindowHandles();
          for (String handle : handles) {
              if (!handle.equals(mainWindow)) {
                  driver.switchTo().window(handle);
                  return;
              }
          }
          throw new NoSuchWindowException("No new window found to switch to.");
      }

      // ---------- Screenshot ----------
      public String takeScreenshotAndSave(String screenshotName) {
          File scrFile = takeScreenShot();
          String destFile = screenshotName + ".png";
          String filePath = File.separator + destFile;
          try {
              FileUtils.copyFile(scrFile, new File(filePath));
          } catch (IOException e) {
              throw new RuntimeException("Screenshot not saved properly: " + e.getMessage());
          }
          return filePath;
      }

      public String takeScreenshotAndSave() {
          return takeScreenshotAndSave("Screenshot_" + getCurrentTime());
      }

      private File takeScreenShot() {
          return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      }

      // ---------- Alerts ----------
      public void acceptAlert() {
          driver.switchTo().alert().accept();
      }

      public void dismissAlert() {
          driver.switchTo().alert().dismiss();
      }

      // ---------- Other ----------
      public void refresh() {
          driver.navigate().refresh();
      }

      public void highLightElement(WebElement element) {
          js.executeScript("arguments[0].style.border='3px solid red'", element);
      }

      public static String getCurrentTime() {
          return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
      }
  }
  