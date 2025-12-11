package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Utils;

public class WesternWearPage extends BasePage {
    private By categoryFilter = By.id("category-filter");
    private By shirtSizeL = By.xpath("//button[text()='L']");
    private By addToCartButton = By.id("add-to-cart");
    private By proceedToCheckoutButton = By.id("proceed-to-checkout");

    public WesternWearPage(WebDriver driver, FluentWait<WebDriver> wait, Utils utils) {
        super(driver, wait, utils);
    }

    public void selectCategory(String category) throws Exception {
        utils.clickElement(categoryFilter);
        // Assuming a method to select category
    }

    public void selectShirtSizeL() throws Exception {
        utils.clickElement(shirtSizeL);
    }

    public void addToCart() throws Exception {
        utils.clickElement(addToCartButton);
    }

    public void proceedToCheckout() throws Exception {
        utils.clickElement(proceedToCheckoutButton);
    }
}