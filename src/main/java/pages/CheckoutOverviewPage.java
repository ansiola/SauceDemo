package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class CheckoutOverviewPage extends BasePage {

    private final By FINISH_BUTTON = By.id("finish");
    private final By ITEM_TOTAL = By.cssSelector(".summary_subtotal_label");
    private final By TAX = By.cssSelector(".summary_tax_label");
    private final By TOTAL = By.cssSelector(".summary_total_label");
    private final By CART_LIST = By.className("cart_list");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        log.info("CheckoutOverviewPage initialized");
    }

    @Override
    public CheckoutOverviewPage open() {
        log.info("Opening checkout overview page");
        driver.get(BASE_URL + "/checkout-step-two.html");
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(CART_LIST).size() > 0;
        log.debug("CheckoutOverviewPage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for checkout overview page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_LIST));
    }

    public CheckoutCompletePage clickFinish() {
        log.info("Clicking finish button");
        wait.until(ExpectedConditions.elementToBeClickable(FINISH_BUTTON));
        driver.findElement(FINISH_BUTTON).click();
        return new CheckoutCompletePage(driver);
    }

    public String getItemTotal() {
        String total = driver.findElement(ITEM_TOTAL).getText();
        log.debug("Item total: {}", total);
        return total;
    }

    public String getTotal() {
        String total = driver.findElement(TOTAL).getText();
        log.debug("Total: {}", total);
        return total;
    }
}