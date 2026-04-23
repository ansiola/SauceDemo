package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test
    public void testAddAndRemoveItemFromCart() throws InterruptedException {
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);

        Thread.sleep(100); // Даю странице загрузиться

        String productName = productPage.getFirstProductName();

        productPage.addFirstProductToCart();
        Thread.sleep(100);

        Assert.assertEquals(productPage.getCartBadgeCount(), "1");

        productPage.openCart();
        Thread.sleep(100);

        Assert.assertEquals(cartPage.getCartItemsCount(), 1);
        Assert.assertEquals(cartPage.getFirstItemName(), productName);

        cartPage.removeFirstItem();
        Thread.sleep(100);

        Assert.assertEquals(cartPage.getCartItemsCount(), 0);
    }
}