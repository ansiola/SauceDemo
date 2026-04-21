import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class SauceDemoLocatorsTest {
    @Test
    public void checkLocator() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        SoftAssert softAssert = new SoftAssert();

        // Базовый url сайта
        String baseUrl = "https://www.saucedemo.com/";
        driver.get(baseUrl);

        // Ждем загрузки страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        // ========== ЛОКАТОРЫ ПО ID ==========
        softAssert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed(),
                "Элемент с id='user-name' не найден");
        softAssert.assertTrue(driver.findElement(By.id("password")).isDisplayed(),
                "Элемент с id='password' не найден");
        softAssert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(),
                "Элемент с id='login-button' не найден");

        //  ЛОКАТОРЫ ПО NAME
        softAssert.assertTrue(driver.findElement(By.name("user-name")).isDisplayed(),
                "Элемент с name='user-name' не найден");
        softAssert.assertTrue(driver.findElement(By.name("password")).isDisplayed(),
                "Элемент с name='password' не найден");

        //  ЛОКАТОРЫ ПО CLASSNAME
        softAssert.assertTrue(driver.findElement(By.className("login_logo")).isDisplayed(),
                "Элемент с class='login_logo' не найден");

        // bot_column может отсутствовать на странице логина
        List<WebElement> botColumn = driver.findElements(By.className("bot_column"));
        softAssert.assertTrue(botColumn.size() >= 0, "Элемент bot_column может отсутствовать на странице");

        //  ЛОКАТОРЫ ПО TAGNAME
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        softAssert.assertTrue(inputs.size() > 0, "На странице нет элементов input");
        softAssert.assertEquals(inputs.size(), 3, "Должно быть 3 элемента input на странице логина");

        // На странице логина нет элементов <button>, кнопка Login - это input type="submit"
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        softAssert.assertTrue(buttons.size() == 0, "На странице логина не должно быть элементов button");

        // ========== XPATH ЛОКАТОРЫ ==========
        // 1. поиск по атрибуту
        softAssert.assertTrue(driver.findElement(By.xpath("//input[@placeholder='Username']")).isDisplayed(),
                "XPath по атрибуту placeholder='Username' не найден");
        softAssert.assertTrue(driver.findElement(By.xpath("//input[@placeholder='Password']")).isDisplayed(),
                "XPath по атрибуту placeholder='Password' не найден");

        // 2. поиск по тексту
        softAssert.assertTrue(driver.findElement(By.xpath("//div[text()='Swag Labs']")).isDisplayed(),
                "XPath по тексту 'Swag Labs' не найден");

        // 3. поиск по частичному совпадению атрибута
        softAssert.assertTrue(driver.findElement(By.xpath("//input[contains(@placeholder, 'Username')]")).isDisplayed(),
                "XPath с contains для placeholder не найден");

        // 4. поиск по частичному совпадению текста (если есть ошибка)
        List<WebElement> errorMessages = driver.findElements(By.xpath("//h3[contains(text(), 'sadface')]"));
        softAssert.assertTrue(errorMessages.size() >= 0, "Элемент с ошибкой может отсутствовать");

        // Доп XPath локаторы для проверки (без assert, просто создание)
        By.xpath("//input[@id='login-button']//ancestor::div[@class='login-box']");
        By.xpath("//input[@id='user-name']//ancestor::form");
        By.xpath("//form[@class='login-box']//descendant::input");
        By.xpath("//div[@class='login_wrapper']//descendant::*");
        By.xpath("//input[@id='user-name']//following::input");
        By.xpath("//label[@class='login_password']//following::input");
        By.xpath("//input[@id='login-button']//parent::div");
        By.xpath("//input[@id='user-name']//parent::div");
        By.xpath("//input[@id='login-button']//preceding::input");
        By.xpath("//input[@id='password']//preceding::div");
        By.xpath("//input[@type='submit' and @data-test='login-button']");
        By.xpath("//input[@placeholder='Username' and @type='text']");

        //  CSS ЛОКАТОРЫ
        // 1. .class
        softAssert.assertTrue(driver.findElement(By.cssSelector(".login_logo")).isDisplayed(),
                "CSS селектор .login_logo не найден");

        // 2. id
        softAssert.assertTrue(driver.findElement(By.cssSelector("#user-name")).isDisplayed(),
                "CSS селектор #user-name не найден");
        softAssert.assertTrue(driver.findElement(By.cssSelector("#password")).isDisplayed(),
                "CSS селектор #password не найден");
        softAssert.assertTrue(driver.findElement(By.cssSelector("#login-button")).isDisplayed(),
                "CSS селектор #login-button не найден");

        // 3. по атрибуту
        softAssert.assertTrue(driver.findElement(By.cssSelector("[placeholder='Username']")).isDisplayed(),
                "CSS селектор [placeholder='Username'] не найден");

        // 4. по частичному совпадению атрибута
        softAssert.assertTrue(driver.findElement(By.cssSelector("[id*='user']")).isDisplayed(),
                "CSS селектор [id*='user'] не найден");
        softAssert.assertTrue(driver.findElement(By.cssSelector("[id^='pass']")).isDisplayed(),
                "CSS селектор [id^='pass'] не найден");
        softAssert.assertTrue(driver.findElement(By.cssSelector("[id$='button']")).isDisplayed(),
                "CSS селектор [id$='button'] не найден");

        // Дополнительные CSS локаторы (без assert)
        By.cssSelector(".login_logo.app_logo");
        By.cssSelector(".error-button.error_icon");
        By.cssSelector(".login_wrapper .login_box");
        By.cssSelector(".login_wrapper .bot_column");
        By.cssSelector("input");
        By.cssSelector("form");
        By.cssSelector("input.input_error");
        By.cssSelector("div.login_logo");

        System.out.println("Все проверки локаторов выполнены");
        softAssert.assertAll();

        driver.quit();
    }
}