package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Utils;

public class CheckoutPage extends BasePage {
    private By loginEmailField = By.id("login-email");
    private By loginPasswordField = By.id("login-password");
    private By loginButton = By.id("login-button");
    private By paymentMethodCard = By.id("payment-card");
    private By cardNumberField = By.id("card-number");
    private By cardHolderNameField = By.id("cardholder-name");
    private By saveCardCheckbox = By.id("save-card");
    private By errorMessage = By.id("error-message");

    public CheckoutPage(WebDriver driver, FluentWait<WebDriver> wait, Utils utils) {
        super(driver, wait, utils);
    }

    public void login(String email, String password) throws Exception {
        utils.inputText(loginEmailField, email);
        utils.inputText(loginPasswordField, password);
        utils.clickElement(loginButton);
    }

    public void selectCardPaymentMethod() throws Exception {
        utils.clickElement(paymentMethodCard);
    }

    public void enterCardDetails(String cardNumber, String cardHolderName) throws Exception {
        utils.inputText(cardNumberField, cardNumber);
        utils.inputText(cardHolderNameField, cardHolderName);
    }

    public void attemptToSaveCard() throws Exception {
        utils.clickElement(saveCardCheckbox);
    }

    public boolean isErrorMessageDisplayed(String expectedMessage) throws Exception {
        String actualMessage = utils.getText(errorMessage);
        return actualMessage.contains(expectedMessage);
    }
}