package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

@Slf4j
public class CartPage extends BasePage {

    private final By CART_ITEMS = By.cssSelector(".cart_item");
    private final By CART_ITEM_NAMES = By.cssSelector(".inventory_item_name");
    private final By CART_ITEM_PRICES = By.cssSelector(".inventory_item_price");
    private final By CHECKOUT_BUTTON = By.id("checkout");
    private final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private final By REMOVE_BUTTONS = By.cssSelector(".cart_button");
    private final By CART_CONTENT = By.className("cart_contents_container");

    public CartPage(WebDriver driver) {
        super(driver);
        log.info("CartPage initialized");
    }

    @Override
    public CartPage open() {
        log.info("Opening cart page");
        driver.get(BASE_URL + "/cart.html");
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(CART_CONTENT).size() > 0;
        log.debug("CartPage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for cart page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_CONTENT));
    }

    public int getCartItemsCount() {
        int count = driver.findElements(CART_ITEMS).size();
        log.debug("Cart items count: {}", count);
        return count;
    }

    public String getFirstItemName() {
        String name = driver.findElement(CART_ITEM_NAMES).getText();
        log.debug("First cart item name: {}", name);
        return name;
    }

    public CartPage proceedToCheckout() {
        log.info("Proceeding to checkout");
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON));
        driver.findElement(CHECKOUT_BUTTON).click();
        return this;
    }

    public ProductPage continueShopping() {
        log.info("Continuing shopping");
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
        return new ProductPage(driver);
    }

    public CartPage removeFirstItem() {
        log.info("Removing first item from cart");
        List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTONS);
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
            log.debug("First item removed");
        }
        return this;
    }

    public CartPage removeItemByIndex(int index) {
        log.info("Removing item at index {} from cart", index);
        List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTONS);
        if (index < removeButtons.size()) {
            removeButtons.get(index).click();
            log.debug("Item at index {} removed", index);
        }
        return this;
    }

    public CheckoutPage goToCheckout() {
        log.info("Going to checkout page");
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON));
        driver.findElement(CHECKOUT_BUTTON).click();
        return new CheckoutPage(driver);
    }
}