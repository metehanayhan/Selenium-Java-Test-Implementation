package Ders_05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class C01_Actions {
    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void dragAndDropTest() {
        // 1. https://demoqa.com/droppable adresine gidin.
        driver.get("https://demoqa.com/droppable");

        // 2. "Drag me" elementini "Drop here" kutusunun ustune birakin.
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));

        // Actions objesi olustur.
        Actions actions = new Actions(driver);

        // dragAndDrop metodunu kullan ve perform() ile eylemi gerceklestir.
        actions.dragAndDrop(source, target).perform();

        // 3. "Drop here" kutusunun metninin "Dropped!" oldugunu dogrulayin.
        String actualText = target.getText();
        Assert.assertEquals("Dropped!", actualText);
        System.out.println("Test Basarili! Element dogru yere suruklendi.");
    }
}