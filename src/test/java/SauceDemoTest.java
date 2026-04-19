import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.Assert;

public class SauceDemoTest {
    @Test
    public void testAddProductToCart() throws Exception {
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
        driver.findElement(By.xpath("//button[contains(text(), 'Add to cart')]")).click();

        //  Перейдем в корзину
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        //  Проверим стоимость товара и его имя в корзине
        String actualName = driver.findElement(By.cssSelector(".inventory_item_name")).getText();
        String actualPrice = driver.findElement(By.cssSelector(".inventory_item_price")).getText();

        Assert.assertEquals(actualName, expectedName, "Имя товара не совпадает");
        Assert.assertEquals(actualPrice, expectedPrice, "Цена товара не совпадает");

        System.out.println(" Тест пройден! Товар: " + actualName + " (" + actualPrice + ")");

        driver.quit();  //
    }
}
