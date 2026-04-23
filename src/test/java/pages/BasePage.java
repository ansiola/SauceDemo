package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public final String BASE_URL = "https://www.saucedemo.com";

    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null!");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}