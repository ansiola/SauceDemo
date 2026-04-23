package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    // Step 1: Your Information
    private final By FIRST_NAME_FIELD = By.id("first-name");
    private final By LAST_NAME_FIELD = By.id("last-name");
    private final By POSTAL_CODE_FIELD = By.id("postal-code");
    private final By CONTINUE_BUTTON = By.id("continue");
    private final By CANCEL_BUTTON = By.id("cancel");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    // Step 2: Overview
    private final By FINISH_BUTTON = By.id("finish");
    private final By ITEM_TOTAL = By.cssSelector(".summary_subtotal_label");
    private final By TAX = By.cssSelector(".summary_tax_label");
    private final By TOTAL = By.cssSelector(".summary_total_label");

    // Step 3: Complete
    private final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private final By BACK_HOME_BUTTON = By.id("back-to-products");

    // ВАЖНО: Добавьте этот конструктор!
    public CheckoutPage(WebDriver driver) {
        super(driver);  // Вызов конструктора родителя
    }

    // Step 1 methods
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        driver.findElement(POSTAL_CODE_FIELD).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(CONTINUE_BUTTON).click();
    }

    public void clickCancel() {
        driver.findElement(CANCEL_BUTTON).click();
    }

    public String getCheckoutErrorMessage() {
        return driver.findElement(ERROR_MESSAGE).getText();
    }

    // Step 2 methods
    public void clickFinish() {
        driver.findElement(FINISH_BUTTON).click();
    }

    public String getItemTotal() {
        return driver.findElement(ITEM_TOTAL).getText();
    }

    public String getTax() {
        return driver.findElement(TAX).getText();
    }

    public String getTotal() {
        return driver.findElement(TOTAL).getText();
    }

    // Step 3 methods
    public String getCompleteMessage() {
        return driver.findElement(COMPLETE_HEADER).getText();
    }

    public void backHome() {
        driver.findElement(BACK_HOME_BUTTON).click();
    }
}