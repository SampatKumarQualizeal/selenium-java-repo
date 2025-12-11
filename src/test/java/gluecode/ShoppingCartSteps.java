package gluecode;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CheckoutPage;
import pages.FabindiaHomePage;
import pages.WesternWearPage;

public class ShoppingCartSteps {
    private Shared shared;
    private FabindiaHomePage fabindiaHomePage;
    private WesternWearPage westernWearPage;
    private CheckoutPage checkoutPage;

    public ShoppingCartSteps(Shared shared) {
        this.shared = shared;
        this.fabindiaHomePage = new FabindiaHomePage(shared.driver, shared.wait, shared.utils);
        this.westernWearPage = new WesternWearPage(shared.driver, shared.wait, shared.utils);
        this.checkoutPage = new CheckoutPage(shared.driver, shared.wait, shared.utils);
    }

    @Given("the user has access to the Fabindia website")
    public void userAccessFabindiaWebsite() throws Exception {
        // Already handled in Shared class setup
    }

    @And("the Chrome browser is installed")
    public void chromeBrowserIsInstalled() throws Exception {
        // Already handled in Shared class setup
    }

    @And("the user navigates to the Fabindia homepage")
    public void userNavigatesToHomepage() throws Exception {
        // Already handled in Shared class setup
    }

    @And("the user is on the {string} page")
    public void userIsOnPage(String pageName) throws Exception {
        if (pageName.equals("Western Wear")) {
            fabindiaHomePage.navigateToWesternWear();
        }
    }

    @And("the user has selected a {string} from the {string} filter")
    public void userSelectsFromFilter(String item, String filter) throws Exception {
        if (filter.equals("Category") && item.equals("Shirt")) {
            westernWearPage.selectCategory(item);
        }
    }

    @And("the user has selected size {string} for the shirt")
    public void userSelectsShirtSize(String size) throws Exception {
        if (size.equals("L")) {
            westernWearPage.selectShirtSizeL();
        }
    }

    @And("the shirt is added to the cart")
    public void shirtIsAddedToCart() throws Exception {
        westernWearPage.addToCart();
    }

    @And("the user proceeds to checkout")
    public void userProceedsToCheckout() throws Exception {
        westernWearPage.proceedToCheckout();
    }

    @And("the user logs in using email and password")
    public void userLogsIn() throws Exception {
        String email = shared.properties.getProperty("username");
        String password = shared.properties.getProperty("password");
        checkoutPage.login(email, password);
    }

    @And("the user continues to the payment page")
    public void userContinuesToPaymentPage() throws Exception {
        // Assuming this action is part of the login or direct navigation post-login
    }

    @And("the user selects {string} as the payment method")
    public void userSelectsPaymentMethod(String paymentMethod) throws Exception {
        if (paymentMethod.equals("Card Payment")) {
            checkoutPage.selectCardPaymentMethod();
        }
    }

    @And("the user enters dummy card details:")
    public void userEntersDummyCardDetails(io.cucumber.datatable.DataTable dataTable) throws Exception {
        String cardNumber = dataTable.cell(1, 0);
        String cardHolderName = dataTable.cell(1, 1);
        checkoutPage.enterCardDetails(cardNumber, cardHolderName);
    }

    @When("the user attempts to save the card")
    public void userAttemptsToSaveCard() throws Exception {
        checkoutPage.attemptToSaveCard();
    }

    @Then("the system should display {string}")
    public void systemDisplaysMessage(String expectedMessage) throws Exception {
        boolean isErrorMessageDisplayed = checkoutPage.isErrorMessageDisplayed(expectedMessage);
        Assert.assertTrue(isErrorMessageDisplayed, "Error message not displayed or incorrect.");
    }
}