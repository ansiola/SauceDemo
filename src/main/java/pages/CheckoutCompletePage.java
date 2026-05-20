package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class CheckoutCompletePage extends BasePage {

    private final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private final By BACK_HOME_BUTTON = By.id("back-to-products");
    private final By COMPLETE_CONTAINER = By.id("checkout_complete_container");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        log.info("CheckoutCompletePage initialized");
    }

    @Override
    public CheckoutCompletePage open() {
        log.info("Opening checkout complete page");
        driver.get(BASE_URL + "/checkout-complete.html");
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(COMPLETE_CONTAINER).size() > 0;
        log.debug("CheckoutCompletePage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for checkout complete page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_CONTAINER));
    }

    public String getCompleteMessage() {
        String message = driver.findElement(COMPLETE_HEADER).getText();
        log.info("Order complete message: {}", message);
        return message;
    }

    public ProductPage backHome() {
        log.info("Returning to products page");
        driver.findElement(BACK_HOME_BUTTON).click();
        return new ProductPage(driver);
    }
}