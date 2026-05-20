package tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class LoginTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @DataProvider(name = "Тестовые данные для негативного логина", indices = {0, 2})
    public Object[][] loginData() {
        return new Object[][]{
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"test", "test", "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    @Test(groups = {"login", "smoke"},
            testName = "Проверка входа с валидными данными",
            description = "Позитивный тест логина с валидными учетными данными",
            priority = 1)
    public void checkLoginWithPositiveCreds() {
        log.info("Starting positive login test");

        // Использование Chain of Invocations
        ProductPage productPage = loginPage
                .open()
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickLogin();  // Теперь возвращает ProductPage

        assertEquals("Products", productPage.getTitle());
        log.info("Positive login test completed successfully");
    }

    @Test(groups = {"login", "smoke"},
            testName = "Проверка входа с использованием комбинированного метода",
            description = "Альтернативный способ логина",
            priority = 1)
    public void checkLoginWithCombinedMethod() {
        log.info("Starting combined login test");

        ProductPage productPage = loginPage
                .open()
                .loginAs(USERNAME, PASSWORD);  // Метод loginAs теперь существует

        assertEquals("Products", productPage.getTitle());
        log.info("Combined login test completed successfully");
    }

    @Test(groups = {"login", "negative"},
            testName = "Негативные сценарии логина",
            description = "Негативные сценарии логина с неверными учетными данными",
            priority = 2,
            dataProvider = "Тестовые данные для негативного логина")
    public void negativeLogin(String user, String password, String errorMessage) {
        log.info("Starting negative login test for user: {}", user.isEmpty() ? "[empty]" : user);

        // Используем специальный метод для негативного логина
        LoginPage currentPage = loginPage
                .open()
                .enterUsername(user)
                .enterPassword(password)
                .clickLoginExpectingError();  // Возвращает LoginPage для проверки ошибки

        assertEquals(currentPage.getErrorMessage(), errorMessage);
        log.info("Negative login test completed successfully");
    }

    // Дополнительный тест с использованием комбинированного метода для негативного сценария
    @Test(groups = {"login", "negative"},
            testName = "Негативные сценарии логина (комбинированный метод)",
            description = "Негативные сценарии с использованием комбинированного метода",
            priority = 3)
    public void negativeLoginCombined() {
        log.info("Starting negative login combined test");

        String invalidUser = "invalid_user";
        String invalidPassword = "invalid_pass";
        String expectedError = "Epic sadface: Username and password do not match any user in this service";

        LoginPage currentPage = loginPage
                .open()
                .loginAsExpectingError(invalidUser, invalidPassword);

        assertEquals(currentPage.getErrorMessage(), expectedError);
        log.info("Negative login combined test completed successfully");
    }
}