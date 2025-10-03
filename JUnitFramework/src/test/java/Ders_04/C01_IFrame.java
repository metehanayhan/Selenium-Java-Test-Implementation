package Ders_04;

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

public class C01_IFrame {

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
    public void iframeTest() {
        // 1. https://the-internet.herokuapp.com/iframe adresine gidin.
        driver.get("https://the-internet.herokuapp.com/iframe");

        // 2. "An IFrame containing..." basliginin erisilebilir oldugunu test edin ve konsolda yazdirin.
        WebElement baslikElementi = driver.findElement(By.xpath("//h3"));
        System.out.println("Ana Sayfa Basligi: " + baslikElementi.getText());
        Assert.assertTrue("Baslik gorunur degil!", baslikElementi.isDisplayed());

        // 3. Text Box'a "Merhaba Dunya!" yazin.
        // DİKKAT: Text Box, bir IFrame icinde oldugu icin dogrudan erisemeyiz.
        // Once IFrame'e gecis (switch) yapmamiz gerekir.
        driver.switchTo().frame("mce_0_ifr"); // IFrame'in id'si ile gecis yaptik.

        WebElement textBox = driver.findElement(By.xpath("//p"));
        textBox.clear(); // Icindeki mevcut metni temizledik.
        textBox.sendKeys("Merhaba Dunya!");

        // 4. TextBox'in altinda bulunan "Elemental Selenium" linkinin gorunur oldugunu dogrulayin.
        // DİKKAT: Bu link IFrame'in disinda, ana sayfada.
        // Bu yuzden IFrame'den geri cikmamiz gerekiyor.
        driver.switchTo().defaultContent(); // Ana sayfaya geri donduk.

        WebElement elementalSeleniumLinki = driver.findElement(By.linkText("Elemental Selenium"));
        System.out.println("Ana Sayfa Link Metni: " + elementalSeleniumLinki.getText());
        Assert.assertTrue("Elemental Selenium linki gorunur degil!", elementalSeleniumLinki.isDisplayed());
    }
}