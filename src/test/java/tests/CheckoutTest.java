package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CheckoutTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";
    private final String FIRST_NAME = "John";
    private final String LAST_NAME = "Doe";
    private final String POSTAL_CODE = "12345";

    private void loginAndAddProduct() {
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);
        productPage.addFirstProductToCart();
        productPage.openCart();
        cartPage.proceedToCheckout();
    }

    @Test(groups = {"checkout", "smoke"},
            testName = "Оформление заказа",
            description = "Полный позитивный сценарий оформления заказа",
            priority = 1)
    public void testCompleteCheckoutProcess() {
        loginAndAddProduct();
        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        Assert.assertEquals(checkoutPage.getCompleteMessage(), "Thank you for your order!");
    }

    @Test(groups = {"checkout", "negative"},
            testName = "Оформление с пустым именем",
            description = "Негативный сценарий: Оформление с пустым именем",
            priority = 2)
    public void testCheckoutWithEmptyFirstName() {
        loginAndAddProduct();
        checkoutPage.fillCheckoutInformation("", LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();
        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(), "Error: First Name is required");
    }

    @Test(groups = {"checkout", "negative"},
            testName = "Оформление с пустой фамилией",
            description = "Негативный сценарий: Оформление с пустой фамилией",
            priority = 2)
    public void testCheckoutWithEmptyLastName() {
        loginAndAddProduct();
        checkoutPage.fillCheckoutInformation(FIRST_NAME, "", POSTAL_CODE);
        checkoutPage.clickContinue();
        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(), "Error: Last Name is required");
    }

    @Test(groups = {"checkout", "negative"},
            testName = "Оформление с пустым индексом",
            description = "Негативный сценарий: Оформление с пустым индексом",
            priority = 2)
    public void testCheckoutWithEmptyPostalCode() {
        loginAndAddProduct();
        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, "");
        checkoutPage.clickContinue();
        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(), "Error: Postal Code is required");
    }

    @Test(groups = {"checkout"},
            testName = "Отмена заказа",
            description = "Отмена оформления заказа",
            priority = 3)
    public void testCancelCheckout() {
        loginAndAddProduct();
        checkoutPage.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @Test(groups = {"checkout"}, description = "Возврат на главную после оформления", priority = 4)
    public void testBackHomeAfterCheckout() {
        SoftAssert softAssert = new SoftAssert();
        loginAndAddProduct();
        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        checkoutPage.backHome();
        softAssert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        softAssert.assertEquals(productPage.getTitle(), "Products");
        softAssert.assertAll();
    }
}