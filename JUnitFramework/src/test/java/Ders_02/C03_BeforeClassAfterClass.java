package Ders_02;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C03_BeforeClassAfterClass {

    static WebDriver driver;   // testten de erişebilmesi için global yaptık
    @BeforeClass
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();  // her bir sekme kapansın diye quit yaptık.. close sadece en son sekmeyi kapatır
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

    /*
    Bu notasyonlar, JUnit test framework'ünde kullanılan iki önemli anotasyondur ve test sınıfının yaşam döngüsünü yönetmek için kullanılırlar.
    @BeforeClass: Bu anotasyon, test sınıfındaki tüm test metotları çalıştırılmadan önce bir kez çalıştırılacak olan metodu belirtir. Bu metodun static
    olması gerekir. Genellikle, test ortamını hazırlamak için kullanılır, örneğin, WebDriver'ı başlatmak, veritabanı bağlantılarını açmak gibi işlemler
    burada yapılır.
    @AfterClass: Bu anotasyon ise test sınıfındaki tüm test metotları çalıştırıldıktan sonra bir kez çalıştırılacak olan metodu belirtir. Bu metodun da
    static olması gerekir. Genellikle, test ortamını temizlemek için kullanılır, örneğin, WebDriver'ı kapatmak, veritabanı bağlantılarını kapatmak gibi
    işlemler burada yapılır.

    before after dan farkı: beforeclass ve afterclass static olmak zorunda
    beforeclass ve afterclass testlerden önce ve sonra 1 kere çalışır
    before ve after her testten önce ve sonra çalışır
    hepsini aynı browser penceresinde yapar ve sıra ile yapar sonra kapatır
     */
}
