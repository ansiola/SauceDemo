package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage extends BasePage {

    private final By CART_ITEMS = By.cssSelector(".cart_item");
    private final By CART_ITEM_NAMES = By.cssSelector(".inventory_item_name");
    private final By CART_ITEM_PRICES = By.cssSelector(".inventory_item_price");
    private final By CHECKOUT_BUTTON = By.id("checkout");
    private final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private final By REMOVE_BUTTONS = By.cssSelector(".cart_button");

    // ВАЖНО: Добавьте этот конструктор!
    public CartPage(WebDriver driver) {
        super(driver);  // Вызов конструктора родителя
    }

    public int getCartItemsCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public String getFirstItemName() {
        return driver.findElement(CART_ITEM_NAMES).getText();
    }

    public String getFirstItemPrice() {
        return driver.findElement(CART_ITEM_PRICES).getText();
    }

    public void proceedToCheckout() {
        driver.findElement(CHECKOUT_BUTTON).click();
    }

    public void continueShopping() {
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
    }

    public void removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTONS);
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
    }

    public List<String> getAllItemNames() {
        return driver.findElements(CART_ITEM_NAMES)
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}