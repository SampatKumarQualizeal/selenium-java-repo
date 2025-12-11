package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Utils;

public class BasePage {
    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    protected Utils utils;

    public BasePage(WebDriver driver, FluentWait<WebDriver> wait, Utils utils) {
        this.driver = driver;
        this.wait = wait;
        this.utils = utils;
        PageFactory.initElements(driver, this);
    }
}