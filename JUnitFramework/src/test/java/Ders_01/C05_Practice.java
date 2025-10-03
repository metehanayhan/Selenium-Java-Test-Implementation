package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class C05_Practice {
    public static void main(String[] args) throws InterruptedException {
        // chromedriver i kullanarak bir driver olusturalim
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // facebook sayfasina gidin ve sayfa basliginin "facebook" olup olmadigini kontrol edin
        driver.get("https://www.facebook.com");
        // degilse dogru basligi yazdirin
        String actualTitle = driver.getTitle();
        String expectedTitle = "facebook";
        if (actualTitle.equals(expectedTitle)){
            System.out.println("Title facebook ile ayni");
        } else {
            System.out.println("Title facebook ile ayni degil, actual title : " + actualTitle);
        }

        // sayfa url inin "facebook" icerip icermedigini kontrol edin icermiyorsa actual url i yazdirin
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "facebook";
        if (actualUrl.contains(expectedUrl)){
            System.out.println("Url facebook iceriyor");
        } else {
            System.out.println("Url facebook icermiyor, actual url : " + actualUrl);
        }

        // walmart sayfasina gidin ve sayfa basliginin "Walmart.com" icerip icermedigini kontrol edin
        driver.get("https://www.walmart.com");
        String actualWalmartTitle = driver.getTitle();
        String expectedWalmartTitle = "Walmart.com";
        if (actualWalmartTitle.contains(expectedWalmartTitle)){
            System.out.println("Walmart title Walmart.com iceriyor");
        } else {
            System.out.println("Walmart title Walmart.com icermiyor, actual title : " + actualWalmartTitle);
        }

        // tekrar facebook sayfasina donun ve sayfayi yenileyin
        driver.navigate().back();
        Thread.sleep(2000);
        driver.navigate().refresh();

        // sayfayi maximaze yapin browser i kapatin
        driver.manage().window().maximize();
        driver.close();

    }
}
