package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

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
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);
        assertEquals("Products", productPage.getTitle());
    }

    @Test(groups = {"login", "negative"},
            testName = "Негативные сценарии логина",
            description = "Негативные сценарии логина с неверными учетными данными",
            priority = 2,
            dataProvider = "Тестовые данные для негативного логина")
    public void negativeLogin(String user, String password, String errorMessage) {
        loginPage.open();
        loginPage.login(user, password);
        assertEquals(loginPage.getErrorMessage(), errorMessage);
    }
}