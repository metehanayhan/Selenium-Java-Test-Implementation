package Ders_02;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C01_Junit {
    @Test
    public void Test(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.google.com");

    }

    @Test
    public void Test2(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.walmart.com");
    }
}

/*
@Test nedir: Bu annotation'a sahip method'lar JUnit tarafindan test method'u olarak tanimlanir ve calistirilir.
ayrıca main method'a ihtiyac duymadan bu method'lar calistirilabilir.
hem de daha duzenli ve anlasilir kod yazmamizi saglar.
c01_junit class'inda iki farkli test method'u olusturduk. çalıstırdıgımızda iki method'da calısır.2 farkli sekme acılır
Before ve After method'ları ile birlikte kullanılır.
 */