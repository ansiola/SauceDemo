package tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductPage;

@Slf4j
public class ProductTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test(groups = {"product", "smoke"},
            testName = "Добавление товаров в корзину",
            description = "Добавление товара в корзину со страницы товаров",
            priority = 1)
    public void testAddProductToCart() {
        log.info("Starting test: add product to cart");
        SoftAssert softAssert = new SoftAssert();

        // Исправлено: login() возвращает ProductPage
        ProductPage products = loginPage.open()
                .login(USERNAME, PASSWORD);

        String productName = products.getFirstProductName();
        log.debug("Product name: {}", productName);

        products.addFirstProductToCart();

        // Исправлено: сравниваем int с int, а не String с String
        softAssert.assertEquals(products.getCartBadgeCount(), 1, "Cart badge count should be 1");

        products.openCart();
        softAssert.assertEquals(cartPage.getFirstItemName(), productName, "Cart item name doesn't match");

        softAssert.assertAll();
        log.info("Test completed successfully");
    }

    @Test(groups = {"product"},
            testName = "Обновление счетчика корзины",
            description = "Проверка обновления счетчика корзины при добавлении нескольких товаров",
            priority = 2)
    public void testCartBadgeUpdates() {
        log.info("Starting test: cart badge updates");
        SoftAssert softAssert = new SoftAssert();

        // Исправлено: login() возвращает ProductPage
        ProductPage products = loginPage.open()
                .login(USERNAME, PASSWORD);

        products.addProductToCartByIndex(0);
        softAssert.assertEquals(products.getCartBadgeCount(), 1, "Cart badge should show 1 after first addition");

        products.addProductToCartByIndex(1);
        softAssert.assertEquals(products.getCartBadgeCount(), 2, "Cart badge should show 2 after second addition");

        softAssert.assertAll();
        log.info("Test completed successfully");
    }
}