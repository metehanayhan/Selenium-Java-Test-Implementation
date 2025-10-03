package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C04_Locaters {
    public  static void main(String[] args) {
        // locators : web elementleri bulmak icin kullanilan yontemlerdir
        // 1- id, name, className, tagName, linkText, partialLinkText, xpath, cssSelector

        //chrome driver i setup edelim
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //amazon sayfasina gidelim
        driver.get("https://www.amazon.com.tr");

        //amasonda notebook aratalim
       WebElement cerezler = driver.findElement(org.openqa.selenium.By.id("sp-cc-rejectall-link"));
       cerezler.click();

        WebElement aramaKutusu = driver.findElement(org.openqa.selenium.By.id("twotabsearchtextbox"));
        aramaKutusu.sendKeys("notebook" + Keys.ENTER);

        // Amazon sayfasindaki taglerin a olanların sayısını yazdırın
        System.out.println(driver.findElements(By.tagName("a")).size());

        // Amazon sayfasindaki taglerin img olanların sayısını yazdırın
        System.out.println(driver.findElements(By.tagName("img")).size());

        //sayfayi kapatalim
        driver.close();



    }
}
