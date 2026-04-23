package oldTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTest {
    @Test
    public void testAddProductToCart() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //  Залогинимся
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Запоминаем данные первого товара
        String expectedName = driver.findElement(By.cssSelector(".inventory_item_name")).getText();
        String expectedPrice = driver.findElement(By.cssSelector(".inventory_item_price")).getText();

        //  Добавим товар в корзину
        By addToCartButton = By.xpath("(//button[contains(text(), 'Add to cart')])[1]");
        driver.findElement(addToCartButton).click();

        //  Перейдем в корзину
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Проверяем стоимость товара и его имя в корзине с помощью SoftAssert
        String actualName = driver.findElement(By.cssSelector(".inventory_item_name")).getText();
        String actualPrice = driver.findElement(By.cssSelector(".inventory_item_price")).getText();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualName, expectedName, "Имя товара не совпадает");
        softAssert.assertEquals(actualPrice, expectedPrice, "Цена товара не совпадает");

        // Выводим все накопленные ошибки
        softAssert.assertAll();

        System.out.println(" Тест пройден! Товар: " + actualName + " (" + actualPrice + ")");

        driver.quit();  //
    }
}