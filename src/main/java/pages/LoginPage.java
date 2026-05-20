package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class LoginPage extends BasePage {

    private final By USERNAME_FIELD = By.id("user-name");
    private final By PASSWORD_FIELD = By.id("password");
    private final By LOGIN_BUTTON = By.id("login-button");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private final By LOGIN_LOGO = By.className("login_logo");

    public LoginPage(WebDriver driver) {
        super(driver);
        log.info("LoginPage initialized");
    }

    @Override
    public LoginPage open() {
        log.info("Opening login page: {}", BASE_URL);
        driver.get(BASE_URL);
        waitForPageLoad();
        return this;
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = driver.findElements(LOGIN_LOGO).size() > 0;
        log.debug("LoginPage loaded: {}", isLoaded);
        return isLoaded;
    }

    @Override
    protected void waitForPageLoad() {
        log.debug("Waiting for login page to load");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_LOGO));
    }

    // Chain of Invocations - каждый метод возвращает this
    public LoginPage enterUsername(String username) {
        log.info("Entering username: {}", username.isEmpty() ? "[empty]" : username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        driver.findElement(USERNAME_FIELD).clear();
        driver.findElement(USERNAME_FIELD).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password: {}", password.isEmpty() ? "[empty]" : "***".repeat(password.length()));
        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        return this;
    }

    // Метод для позитивного логина (возвращает ProductPage)
    public ProductPage clickLogin() {
        log.info("Clicking login button");
        driver.findElement(LOGIN_BUTTON).click();

        // Ожидаем загрузки страницы продуктов
        ProductPage productPage = new ProductPage(driver);
        productPage.waitForPageLoad();
        return productPage;
    }

    // Метод для негативного логина (возвращает LoginPage для проверки ошибки)
    public LoginPage clickLoginExpectingError() {
        log.info("Clicking login button (expecting error)");
        driver.findElement(LOGIN_BUTTON).click();

        // Ожидаем появления сообщения об ошибке
        wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return this;
    }

    // Комбинированный метод для позитивного логина
    public ProductPage loginAs(String username, String password) {
        log.info("Logging in as user: {}", username);
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    // Метод loginSuccess (синоним для loginAs)
    public ProductPage loginSuccess(String username, String password) {
        log.info("Login success as user: {}", username);
        return loginAs(username, password);
    }

    // Комбинированный метод для негативного логина
    public LoginPage loginAsExpectingError(String username, String password) {
        log.info("Attempting negative login as user: {}", username.isEmpty() ? "[empty]" : username);
        return enterUsername(username)
                .enterPassword(password)
                .clickLoginExpectingError();
    }

    // Метод для обратной совместимости (возвращает ProductPage)
    public ProductPage login(String username, String password) {
        return loginAs(username, password);
    }

    public String getErrorMessage() {
        String error = driver.findElement(ERROR_MESSAGE).getText();
        log.warn("Login error message: {}", error);
        return error;
    }
}