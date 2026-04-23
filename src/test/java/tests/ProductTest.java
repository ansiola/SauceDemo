package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test
    public void testAddProductToCart() {
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);

        String productName = productPage.getFirstProductName();
        productPage.addFirstProductToCart();

        Assert.assertEquals(productPage.getCartBadgeCount(), "1");

        productPage.openCart();
        Assert.assertEquals(cartPage.getFirstItemName(), productName);
    }

    @Test
    public void testCartBadgeUpdates() {
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);

        productPage.addProductToCartByIndex(0);
        Assert.assertEquals(productPage.getCartBadgeCount(), "1");

        productPage.addProductToCartByIndex(1);
        Assert.assertEquals(productPage.getCartBadgeCount(), "2");
    }
}