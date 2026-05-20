package tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;

@Slf4j
public class CartTests extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test(groups = {"cart", "smoke"},
            testName = "Добавление и удаление товара из корзины",
            description = "Добавление и удаление товара из корзины",
            priority = 1)
    public void testAddAndRemoveItemFromCart() {
        log.info("Starting cart test: add and remove item");

        // Вариант 1: Используем loginSuccess() который возвращает ProductPage
        String productName = loginPage
                .open()
                .loginSuccess(USERNAME, PASSWORD)
                .getFirstProductName();

        log.info("Selected product: {}", productName);

        CartPage cartPage = productPage
                .addFirstProductToCart()
                .openCart();

        Assert.assertEquals(cartPage.getCartItemsCount(), 1);
        Assert.assertEquals(cartPage.getFirstItemName(), productName);

        cartPage.removeFirstItem();
        Assert.assertEquals(cartPage.getCartItemsCount(), 0);

        log.info("Cart test completed successfully");
    }

    @Test(groups = {"cart"},
            testName = "Проверка возможности продолжения покупок",
            description = "Возврат из корзины на страницу товаров",
            priority = 2)
    public void testContinueShopping() {
        log.info("Starting continue shopping test");

        // Вариант 1: Используем loginSuccess()
        ProductPage productPageAfterReturn = loginPage
                .open()
                .loginSuccess(USERNAME, PASSWORD)
                .addFirstProductToCart()
                .openCart()
                .continueShopping();

        Assert.assertEquals(productPageAfterReturn.getTitle(), "Products");
        log.info("Continue shopping test completed");
    }
}