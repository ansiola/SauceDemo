import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.HashMap;

public class SauceDemoLocator {
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


        // Базовый url сайта
        String baseUrl = "https://www.saucedemo.com/";

        //    локаторы по id  
        By.id("user-name");
        By.id("password");
        By.id("login-button");

        //    локаторы по name  
        By.name("user-name");
        By.name("password");
        By.name("login-button");

        //    локаторы по className  
        By.className("login_logo");
        By.className("error_icon");
        By.className("bot_column");

        //    локаторы по tagName  
        By.tagName("input");
        By.tagName("button");
        By.tagName("div");

        //    локаторы по linkText  
        By.linkText("Twitter");

        //    локаторы по partialLinkText  
        By.partialLinkText("Twit");

        //   xpath локаторы
        // 1. поиск по атрибуту
        By.xpath("//input[@placeholder='Username']");
        By.xpath("//input[@placeholder='Password']");
        By.xpath("//input[@data-test='login-button']");

        // 2. поиск по тексту
        By.xpath("//div[text()='Swag Labs']");
        By.xpath("//h3[text()='Epic sadface: Username is required']");
        
       // 3. поиск по частичному совпадению атрибута
        By.xpath("//input[contains(@data-test, 'login')]");
        By.xpath("//input[contains(@placeholder, 'user')]");

        // 4. поиск по частичному совпадению текста
        By.xpath("//h3[contains(text(), 'sadface')]");
        By.xpath("//div[contains(text(), 'Swag')]");

        // 5. ancestor (предок)
        By.xpath("//input[@id='login-button']//ancestor::div[@class='login-box']");
        By.xpath("//input[@id='user-name']//ancestor::form");

        // 6. descendant (          потомок)
        By.xpath("//form[@class='login-box']//descendant::input");
        By.xpath("//div[@class='login_wrapper']//descendant::*");

        // 7. following (следующие элементы)
        By.xpath("//input[@id='user-name']//following::input");
        By.xpath("//label[@class='login_password']//following::input");

        // 8. parent (родитель)
        By.xpath("//input[@id='login-button']//parent::div");
        By.xpath("//input[@id='user-name']//parent::div");

        // 9. preceding (предшествующие элементы)
        By.xpath("//input[@id='login-button']//preceding::input");
        By.xpath("//input[@id='password']//preceding::div");

        // 10. AND условие
        By.xpath("//input[@type='submit' and @data-test='login-button']");
        By.xpath("//input[@placeholder='Username' and @type='text']");

        // css  локаторы
        // 1. .class
        By.cssSelector(".login_logo");
        By.cssSelector(".bot_column");

        // 2. .class1.class2
        By.cssSelector(".login_logo.app_logo");
        By.cssSelector(".error-button.error_icon");

        // 3. .class1 .class2
        By.cssSelector(".login_wrapper .login_box");
        By.cssSelector(".login_wrapper .bot_column");

        // id
        By.cssSelector("#user-name");
        By.cssSelector("#password");
        By.cssSelector("#login-button");

        // 5. tagname
        By.cssSelector("input");
        By.cssSelector("form");

        // 6. tagname.class
        By.cssSelector("input.input_error");
        By.cssSelector("div.login_logo");

        // 7. [attribute=value]
        By.cssSelector("[placeholder='Username']");
        By.cssSelector("[placeholder='Password']");
        By.cssSelector("[data-test='login-button']");

        // 8. [attribute~=value] (содержит слово в значении)
        By.cssSelector("[class~='error']");

        // 9. [attribute|=value] (начинается с value или value-)
        By.cssSelector("[class|='login']");

        // 10. [attribute^=value] (начинается с)
        By.cssSelector("[id^='user']");
        By.cssSelector("[id^='pass']");

        // 11. [attribute$=value] (заканчивается на)
        By.cssSelector("[id$='button']");
        By.cssSelector("[id$='name']");

        // 12. [attribute*=value] (содержит подстроку)
        By.cssSelector("[id*='user']");
        By.cssSelector("[id*='pass']");
        
        driver.quit();  //
    }
}
