package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTests {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test
    public void testValidLogin() {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement dashboard = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
        Assert.assertTrue(dashboard.isDisplayed(), "Dashboard should be visible after login");
    }

    @Test
    public void testInvalidLogin() {
        driver.findElement(By.name("username")).sendKeys("wrongUser");
        driver.findElement(By.name("password")).sendKeys("wrongPass");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMsg = driver.findElement(By.xpath("//p[contains(@class,'oxd-alert-content-text')]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should be shown for invalid login");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
