package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class C06_Practice {
    public static void main(String[] args) throws InterruptedException {

        // webdriver i kullanarak bir driver olusturalim
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //maximize ekran
        driver.manage().window().maximize();

        // heroku.com sayfasina gidelim
        driver.get("https://www.heroku.com");


        // sign up butonuna basalim
        WebElement element = driver.findElement(By.id("logged-out-signup"));
        element.click();

        // First name, last name, e-mail, company elementlerini locate edelim
        WebElement firstName = driver.findElement(By.id("first_name"));
        WebElement lastName = driver.findElement(By.id("last_name"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement company = driver.findElement(By.id("company"));
        Thread.sleep(1000);


        // gerekli bilgileri yazalim
        firstName.sendKeys("Metehan");
        Thread.sleep(1000);
        lastName.sendKeys("Ayhan");
        Thread.sleep(1000);
        email.sendKeys("test@gmail.com");
        Thread.sleep(1000);
        company.sendKeys("Turksat");


    }
}
