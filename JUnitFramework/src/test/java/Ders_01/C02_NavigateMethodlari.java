package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class C02_NavigateMethodlari {
    public static void main(String[] args) throws InterruptedException {

        // 1- chromedriver i kullanarak bir driver olusturalim
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // 2- driver in tum ekrani kaplamasini saglayalim
        driver.manage().window().maximize();

        // navigate: to() methodu ile get() methodu ayni isi yapar
        // farklari: get() methodunda sayfa yuklenene kadar bekler
        // navigate().to() methodunda ise sayfa yuklenmesini beklemez
        // bu nedenle navigate().to() methodu daha hizli calisir
        // ancak sayfa yuklenmeden yapilan islemler hata verebilir
        // bu nedenle navigate().to() methodu kullanildiginda
        // sayfa yuklenene kadar beklemek icin Thread.sleep() kullanilabilir

        // 3- amazon sayfasina gidelim
        driver.navigate().to("https://www.amazon.com.tr");

        // 4- google sayfasina gidelim
        driver.navigate().to("https://www.google.com");

        // 5- amazon sayfasina geri donelim
        Thread.sleep(1000);
        driver.navigate().back();

        // 6- tekrar google sayfasina gidelim
        Thread.sleep(1000);
        driver.navigate().forward();

        // 7- sayfayi yenileyelim
        Thread.sleep(1000);
        driver.navigate().refresh();

        // 8- sayfayi kapatalim
        driver.quit();

        // quit() methodu acik olan tum sayfalari kapatir
        // close() methodu ise sadece aktif olan sayfayi kapatir

    }
}
