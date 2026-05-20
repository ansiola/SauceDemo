package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class CheckoutPage extends BasePage {

    private final By FIRST_NAME_FIELD = By.id("first-name");
    private final By LAST_NAME_FIELD = By.id("last-name");
    private final By POSTAL_CODE_FIELD = By.id("postal-code");
    private final By CONTINUE_BUTTON = By.id("continue");
    private final By CANCEL_BUTTON = By.id("cancel");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private final By FINISH_BUTTON = By.id("finish");
    private final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private final By CHECKOUT_INFO_CONTAINER = By.className("checkout_info");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        log.info("CheckoutPage initialized");
    }

    @Override
    public CheckoutPage open() {
        log.info("Opening checkout page");
        driver.get(BASE_URL + "/checkout-step-one.html");
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(CHECKOUT_INFO_CONTAINER).size() > 0;
        log.debug("CheckoutPage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for checkout page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(CHECKOUT_INFO_CONTAINER));
    }

    // Chain of Invocations для заполнения формы
    public CheckoutPage enterFirstName(String firstName) {
        log.info("Entering first name: {}", firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_FIELD));
        driver.findElement(FIRST_NAME_FIELD).clear();
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        log.info("Entering last name: {}", lastName);
        driver.findElement(LAST_NAME_FIELD).clear();
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        log.info("Entering postal code: {}", postalCode);
        driver.findElement(POSTAL_CODE_FIELD).clear();
        driver.findElement(POSTAL_CODE_FIELD).sendKeys(postalCode);
        return this;
    }

    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        log.info("Filling checkout information for: {} {}", firstName, lastName);
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        log.info("Clicking continue button");
        driver.findElement(CONTINUE_BUTTON).click();
        return new CheckoutOverviewPage(driver);
    }

    public CartPage clickCancel() {
        log.info("Cancelling checkout");
        driver.findElement(CANCEL_BUTTON).click();
        return new CartPage(driver);
    }

    public String getErrorMessage() {
        String error = driver.findElement(ERROR_MESSAGE).getText();
        log.warn("Checkout error: {}", error);
        return error;
    }
}