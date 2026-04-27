package tests;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test
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

    @Test
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