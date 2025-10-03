package Ders_03;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C01_Assertions {
    WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.amazon.com.tr/");
        Thread.sleep(2000);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }



    @Test
    public void test1() {

        String actualURL = driver.getCurrentUrl();
        String expectedURL = "Amazon";
        Assert.assertFalse(actualURL.contains(expectedURL));

        String actualTitle = driver.getCurrentUrl();
        String expectedTitle = "Facebook";
        Assert.assertTrue(actualTitle.contains(expectedTitle));

    }

    @Test
    public void test2() {

        String actualTitle = driver.getCurrentUrl();
        String expectedTitle = "Facebook";
        Assert.assertTrue(actualTitle.contains(expectedTitle));
        /*
        bu test basarisiz olacak cunku bu sayfanin title i facebook icermiyor kücük harf büyük harf duyarlidir.
        bu yüzden test fail verecektir.

         */

    }

    @Test
    public void test3() {

        WebElement logo =driver.findElement(By.cssSelector("#nav-logo-sprites"));
        Assert.assertTrue(logo.isDisplayed());
    }


    /*
    Assertionslar şunlar için kullanılır:
    Doğrulama (Validation): Test senaryolarında, uygulamanın beklenen davranışları gösterip göstermediğini doğrulamak
    için assertions kullanılır. Örneğin, bir web sayfasının doğru başlığı gösterip göstermediğini kontrol etmek için
    assertions kullanılabilir.
    Hata Tespiti (Error Detection): Assertions, test sırasında beklenmeyen durumları tespit etmek için kullanılır.
    Eğer bir assertion başarısız olursa, bu genellikle bir hatanın varlığını gösterir ve testin durdurulmasına neden
    olabilir.
    Test Raporlama: Assertions, test sonuçlarını raporlamak için kullanılır. Başarılı ve başarısız assertions,
    test raporlarında belirtilir, bu da testin genel durumu hakkında bilgi verir.
    Kod Kalitesini Artırma: Assertions, kodun belirli koşulları sağlamasını garanti altına alarak kod kalitesini
    artırır. Bu, özellikle karmaşık uygulamalarda önemlidir.
    Debugging (Hata Ayıklama): Assertions, test sırasında belirli koşulların sağlanıp sağlanmadığını kontrol ederek
    hata ayıklama sürecini kolaylaştırır. Başarısız bir assertion, hatanın nerede olduğunu belirlemeye yardımcı olabilir.
     */
}
