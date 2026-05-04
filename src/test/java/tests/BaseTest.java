package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.AnnotationTransformer;
import utils.DriverManager;

@Listeners(AnnotationTransformer.class)
public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        System.out.println("setUp() browser: " + browser);

        DriverManager.createDriver(browser);
        driver = DriverManager.getDriver();

        // Проверка, что driver не null
        if (driver == null) {
            throw new IllegalStateException("Driver is null after DriverManager.createDriver()");
        }

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        System.out.println("setUp() completed successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("tearDown() called");
        DriverManager.quitDriver();
    }
}