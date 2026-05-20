package pages;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

@Slf4j
@Getter
public class ProductPage extends BasePage {

    private final By PRODUCT_TITLE = By.cssSelector(".title");
    private final By ADD_TO_CART_BUTTONS = By.xpath("//button[contains(text(), 'Add to cart')]");
    private final By SHOPPING_CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private final By SHOPPING_CART_LINK = By.cssSelector(".shopping_cart_link");
    private final By PRODUCT_NAMES = By.cssSelector(".inventory_item_name");
    private final By PRODUCT_PRICES = By.cssSelector(".inventory_item_price");
    private final By SORT_DROPDOWN = By.cssSelector(".product_sort_container");

    public ProductPage(WebDriver driver) {
        super(driver);
        log.info("ProductPage initialized");
    }

    @Override
    public ProductPage open() {
        log.info("Opening product page");
        driver.get(BASE_URL + "/inventory.html");
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(PRODUCT_TITLE).size() > 0;
        log.debug("ProductPage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for product page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE));
    }

    public String getTitle() {
        String title = driver.findElement(PRODUCT_TITLE).getText();
        log.debug("Product page title: {}", title);
        return title;
    }

    public ProductPage addProductToCartByIndex(int index) {
        log.info("Adding product at index {} to cart", index);
        List<WebElement> addButtons = driver.findElements(ADD_TO_CART_BUTTONS);
        if (index < addButtons.size()) {
            addButtons.get(index).click();
            log.debug("Product added to cart successfully");
        } else {
            log.warn("Product index {} not found. Available products: {}", index, addButtons.size());
        }
        return this;
    }

    public ProductPage addFirstProductToCart() {
        log.info("Adding first product to cart");
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTONS));
        List<WebElement> addButtons = driver.findElements(ADD_TO_CART_BUTTONS);
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            log.debug("First product added to cart");
        }
        return this;
    }

    public int getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SHOPPING_CART_BADGE));
            String countText = driver.findElement(SHOPPING_CART_BADGE).getText();
            int count = Integer.parseInt(countText);
            log.debug("Cart badge count: {}", count);
            return count;
        } catch (Exception e) {
            log.debug("Cart badge not visible, returning 0");
            return 0;
        }
    }

    public CartPage openCart() {
        log.info("Opening shopping cart");
        wait.until(ExpectedConditions.elementToBeClickable(SHOPPING_CART_LINK));
        driver.findElement(SHOPPING_CART_LINK).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageLoad();
        return cartPage;
    }

    public String getFirstProductName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_NAMES));
        String name = driver.findElement(PRODUCT_NAMES).getText();
        log.debug("First product name: {}", name);
        return name;
    }

    public ProductPage sortProductsBy(String value) {
        log.info("Sorting products by: {}", value);
        driver.findElement(SORT_DROPDOWN).click();
        driver.findElement(By.cssSelector(String.format("option[value='%s']", value))).click();
        return this;
    }
}