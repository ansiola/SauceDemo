package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test(groups = {"product", "smoke"},
            testName = "Добавление товаров в корзину",
            description = "Добавление товара в корзину со страницы товаров",
            priority = 1)
    public void testAddProductToCart() {
        SoftAssert softAssert = new SoftAssert();
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);
        String productName = productPage.getFirstProductName();
        productPage.addFirstProductToCart();
        softAssert.assertEquals(productPage.getCartBadgeCount(), "1");
        productPage.openCart();
        softAssert.assertEquals(cartPage.getFirstItemName(), productName);
        softAssert.assertAll();
    }

    @Test(groups = {"product"},
            testName = "Обновление счетчика корзины",
            description = "Проверка обновления счетчика корзины при добавлении нескольких товаров",
            priority = 2)
    public void testCartBadgeUpdates() {
        SoftAssert softAssert = new SoftAssert();
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);
        productPage.addProductToCartByIndex(0);
        softAssert.assertEquals(productPage.getCartBadgeCount(), "1");
        productPage.addProductToCartByIndex(1);
        softAssert.assertEquals(productPage.getCartBadgeCount(), "2");
        softAssert.assertAll();
    }
}