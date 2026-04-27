package tests;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class LoginTest extends BaseTest {

    private final String USERNAME = "standard_user";
    private final String PASSWORD = "secret_sauce";

    @Test
    public void checkLoginWithPositiveCreds() {
        loginPage.open();
        loginPage.login(USERNAME, PASSWORD);
        assertEquals(productPage.getTitle(), "Products");
    }

    @Test
    public void checkLoginWithEmptyUser() {
        loginPage.open();
        loginPage.login("", PASSWORD);
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
    }

    @Test
    public void checkLoginWithEmptyPassword() {
        loginPage.open();
        loginPage.login(USERNAME, "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");
    }

    @Test
    public void checkLoginWithNegativeUser() {
        loginPage.open();
        loginPage.login("ABC", PASSWORD);
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match " +
                "any user in this service");
    }

    @Test
    public void checkLoginWithNegativePassword() {
        loginPage.open();
        loginPage.login(USERNAME, "ABC");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match " +
                "any user in this service");
    }
}