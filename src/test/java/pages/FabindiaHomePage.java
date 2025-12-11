package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Utils;

public class FabindiaHomePage extends BasePage {
    private By westernWearLink = By.linkText("Western Wear");

    public FabindiaHomePage(WebDriver driver, FluentWait<WebDriver> wait, Utils utils) {
        super(driver, wait, utils);
    }

    public void navigateToWesternWear() throws Exception {
        utils.clickElement(westernWearLink);
    }
}