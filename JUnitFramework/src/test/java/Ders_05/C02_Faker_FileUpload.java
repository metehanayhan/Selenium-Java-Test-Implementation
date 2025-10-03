package Ders_05;

import com.github.javafaker.Faker;
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

public class C02_Faker_FileUpload {
    WebDriver driver;
    Faker faker;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        faker = new Faker(); // Faker objesini baslat.
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void fileUploadTest() {
        // 1. https://the-internet.herokuapp.com/upload adresine gidelim.
        driver.get("https://the-internet.herokuapp.com/upload");

        // 2. "Dosya Seç" (Choose File) butonunu locate edelim.
        WebElement chooseFileButton = driver.findElement(By.id("file-upload"));

        // 3. Yuklemek istedigimiz dosyanin yolunu belirleyelim.
        // Projemizin icinde, herkesin bilgisayarinda calisacak dinamik bir yol olusturalim.
        // src/test/resources altina "dummy.txt" adinda bir dosya olusturdugunu varsayiyorum.
        String dosyaYolu = System.getProperty("user.dir") + "/src/test/resources/dummy.txt";
        System.out.println("Yuklenecek Dosya Yolu: " + dosyaYolu);

        // 4. sendKeys() ile dosya yolunu butona gonderelim.
        chooseFileButton.sendKeys(dosyaYolu);

        // 5. "Upload" butonuna basalim.
        driver.findElement(By.id("file-submit")).click();

        // 6. "File Uploaded!" textinin goruntulendigini test edelim.
        WebElement successMessage = driver.findElement(By.tagName("h3"));
        Assert.assertTrue(successMessage.isDisplayed());
        Assert.assertEquals("File Uploaded!", successMessage.getText());
        System.out.println("Dosya basariyla yuklendi.");
    }
}