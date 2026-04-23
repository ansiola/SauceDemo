package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

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

    @Test
    public void testCompleteCheckoutProcess() {
        loginAndAddProduct();

        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertEquals(checkoutPage.getCompleteMessage(), "Thank you for your order!");
    }

    @Test
    public void testCheckoutWithEmptyFirstName() {
        loginAndAddProduct();

        checkoutPage.fillCheckoutInformation("", LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();

        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(),
                "Error: First Name is required");
    }

    @Test
    public void testCheckoutWithEmptyLastName() {
        loginAndAddProduct();

        checkoutPage.fillCheckoutInformation(FIRST_NAME, "", POSTAL_CODE);
        checkoutPage.clickContinue();

        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(),
                "Error: Last Name is required");
    }

    @Test
    public void testCheckoutWithEmptyPostalCode() {
        loginAndAddProduct();

        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, "");
        checkoutPage.clickContinue();

        Assert.assertEquals(checkoutPage.getCheckoutErrorMessage(),
                "Error: Postal Code is required");
    }

    @Test
    public void testCancelCheckout() {
        loginAndAddProduct();

        checkoutPage.clickCancel();

        // Должны вернуться на страницу корзины
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @Test
    public void testBackHomeAfterCheckout() {
        loginAndAddProduct();

        checkoutPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        checkoutPage.backHome();

        // Проверяю, что вернулись на главную страницу товаров
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        Assert.assertEquals(productPage.getTitle(), "Products");
    }
}