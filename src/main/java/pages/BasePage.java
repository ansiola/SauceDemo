package pages;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;

@Slf4j
@Getter
public abstract class BasePage {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    public final String BASE_URL = "https://www.saucedemo.com";

    public BasePage(WebDriver driver) {
        if (driver == null) {
            log.error("Driver cannot be null!");
            throw new IllegalArgumentException("Driver cannot be null!");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.debug("BasePage initialized with driver: {}", driver.getClass().getSimpleName());
    }

    // Методы для Loadable Page паттерна
    public abstract BasePage open();

    public abstract boolean isPageLoaded();

    protected void waitForPageLoad() {
        log.debug("Waiting for page to load: {}", this.getClass().getSimpleName());
        // Базовый метод ожидания загрузки страницы
    }
}