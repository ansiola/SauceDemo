package tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutCompletePage;
import pages.CheckoutPage;

@Slf4j
public class CheckoutTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";
    private final String FIRST_NAME = "John";
    private final String LAST_NAME = "Doe";
    private final String POSTAL_CODE = "12345";

    @Test(groups = {"checkout", "smoke"},
            testName = "Оформление заказа",
            description = "Полный позитивный сценарий оформления заказа",
            priority = 1)
    public void testCompleteCheckoutProcess() {
        log.info("Starting complete checkout test");

        CheckoutCompletePage completePage = loginPage
                .open()
                .loginAs(USERNAME, PASSWORD)
                .addFirstProductToCart()
                .openCart()
                .goToCheckout()
                .fillInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE)
                .clickContinue()
                .clickFinish();

        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
        log.info("Checkout test completed successfully");
    }

    @Test(groups = {"checkout", "negative"},
            testName = "Оформление с пустым именем",
            description = "Негативный сценарий: Оформление с пустым именем",
            priority = 2)
    public void testCheckoutWithEmptyFirstName() {
        log.info("Starting checkout test with empty first name");

        CheckoutPage checkoutPage = loginPage
                .open()
                .loginAs(USERNAME, PASSWORD)
                .addFirstProductToCart()
                .openCart()
                .goToCheckout();

        checkoutPage.fillInformation("", LAST_NAME, POSTAL_CODE).clickContinue();

        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: First Name is required");
        log.info("Empty first name test completed");
    }
}