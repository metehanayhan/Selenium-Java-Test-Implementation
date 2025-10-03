package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class C03_ManageMethodlari {
    public static void main(String[] args) throws InterruptedException {

        // chrome driver i setup edelim
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //amazon sayfasina gidelim
        driver.get("https://www.amazon.com");

        //sayfanin konumunu ve boyutlarini yazdirin
        System.out.println("Sayfanin konumu : " + driver.manage().window().getPosition());
        System.out.println("Sayfanin boyutlari : " + driver.manage().window().getSize());

        //sayfayi simge durumuna getirin
        driver.manage().window().minimize();

        // simge durumunda 2 saniye bekleyip sayfayi maximize yapin
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.manage().window().maximize();

        //MAXİMİZE iken sayfanin konumunu ve boyutlarini yazdirin
        System.out.println("Maximize iken sayfanin konumu : " + driver.manage().window().getPosition());
        System.out.println("Maximize iken sayfanin boyutlari : " + driver.manage().window().getSize());

        //sayfayi fullscreen yapin
        driver.manage().window().fullscreen();
        Thread.sleep(1000);

        //fullscreen iken sayfanin konumu ve boyutlarini yazdirin
        System.out.println("Fullscreen iken sayfanin konumu : " + driver.manage().window().getPosition());
        System.out.println("Fullscreen iken sayfanin boyutlari : " + driver.manage().window().getSize());

        //sayfanin boyutunu istediğimiz şekilde ayarlayalim
        driver.manage().window().setPosition(new Point(20,20));
        driver.manage().window().setSize(new Dimension(800,400));
        Thread.sleep(3000);

        //sayfayi kapatin
        driver.close();


    }
}
