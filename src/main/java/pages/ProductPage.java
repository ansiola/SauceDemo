package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ProductPage extends BasePage {

    private final By PRODUCT_TITLE = By.cssSelector(".title");
    private final By ADD_TO_CART_BUTTONS = By.xpath("//button[contains(text(), 'Add to cart')]");
    private final By SHOPPING_CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private final By SHOPPING_CART_LINK = By.cssSelector(".shopping_cart_link");
    private final By PRODUCT_NAMES = By.cssSelector(".inventory_item_name");
    private final By PRODUCT_PRICES = By.cssSelector(".inventory_item_price");
    private final By SORT_DROPDOWN = By.cssSelector(".product_sort_container");

    public ProductPage(WebDriver driver) {
        super(driver);  // Вызов конструктора родителя
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE));
        return driver.findElement(PRODUCT_TITLE).getText();
    }

    public void addFirstProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTONS));
        List<WebElement> addButtons = driver.findElements(ADD_TO_CART_BUTTONS);
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
        }
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> addButtons = driver.findElements(ADD_TO_CART_BUTTONS);
        if (index < addButtons.size()) {
            addButtons.get(index).click();
        }
    }

    public String getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SHOPPING_CART_BADGE));
            return driver.findElement(SHOPPING_CART_BADGE).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(SHOPPING_CART_LINK));
        driver.findElement(SHOPPING_CART_LINK).click();
    }

    public String getFirstProductName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_NAMES));
        return driver.findElement(PRODUCT_NAMES).getText();
    }

    public String getFirstProductPrice() {
        return driver.findElement(PRODUCT_PRICES).getText();
    }

    public void sortProductsBy(String value) {
        driver.findElement(SORT_DROPDOWN).click();
        driver.findElement(By.cssSelector(String.format("option[value='%s']", value))).click();
    }
}