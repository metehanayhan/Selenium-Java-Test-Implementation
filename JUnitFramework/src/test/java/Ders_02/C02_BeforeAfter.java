package Ders_02;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class C02_BeforeAfter {
    WebDriver driver;   // testten de erişebilmesi için global yaptık
    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void Test(){
        driver.get("https://www.amazon.com");
        System.out.println("amazon calisti");

    }

    @Test
    public void Test2(){
        driver.get("https://www.facebook.com");
        System.out.println("facebook calisti");

    }

    @After
    public void tearDown(){
        driver.quit();  // her bir sekme kapansın diye quit yaptık.. close sadece en son sekmeyi kapatır
    }
}
