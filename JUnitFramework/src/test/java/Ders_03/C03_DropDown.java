package Ders_03;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class C03_DropDown {
    WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Thread.sleep(2000);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void dropdowntest(){

        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement ddmList= driver.findElement(By.cssSelector("#dropdown"));
        Select select= new Select(ddmList);
        select.selectByIndex(1);
        select.selectByValue("2");
        select.selectByVisibleText("Option 1");
        System.out.println("Selected Option 1: " + select.getFirstSelectedOption().getText());
        System.out.println("Dropdown Options: ");
        select.getOptions().forEach(t-> System.out.println(t.getText()));
        System.out.println("Dropdown Size: " + select.getOptions().size());

    }



}
