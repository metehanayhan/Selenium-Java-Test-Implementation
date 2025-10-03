package Ders_04;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Set;

public class C02_WindowHandles {
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
        driver.quit(); // quit() acilan tum pencereleri kapatir.
    }

    @Test
    public void windowHandlesTest() {
        // 1. https://the-internet.herokuapp.com/windows adresine gidin.
        driver.get("https://the-internet.herokuapp.com/windows");

        // 2. Sayfadaki textin "Opening a new window" oldugunu dogrulayin.
        String actualText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals("Opening a new window", actualText);

        // 3. Yeni pencere acilmadan once ilk sayfanin handle (kimlik) degerini alin.
        String ilkSayfaHandle = driver.getWindowHandle();
        System.out.println("Ilk Sayfa Handle: " + ilkSayfaHandle);

        // 4. "Click Here" butonuna basin.
        driver.findElement(By.linkText("Click Here")).click();

        // 5. Acilan yeni pencereye gecin.
        Set<String> tumPencereHandles = driver.getWindowHandles();
        String ikinciSayfaHandle = "";

        for (String eachHandle : tumPencereHandles) {
            if (!eachHandle.equals(ilkSayfaHandle)) {
                ikinciSayfaHandle = eachHandle;
                break; // Ikinciyi buldugumuzda donguden cikabiliriz.
            }
        }
        System.out.println("Ikinci Sayfa Handle: " + ikinciSayfaHandle);
        driver.switchTo().window(ikinciSayfaHandle); // Ikinci sayfaya gecis yaptik.

        // 6. Gectiginiz yeni sayfadaki textin "New Window" oldugunu dogrulayin.
        String yeniSayfaText = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals("New Window", yeniSayfaText);

        // 7. Ilk sayfaya geri donun ve sayfa basliginin "The Internet" oldugunu dogrulayin.
        driver.switchTo().window(ilkSayfaHandle); // Ilk sayfaya geri donduk.
        Assert.assertEquals("The Internet", driver.getTitle());
    }
}