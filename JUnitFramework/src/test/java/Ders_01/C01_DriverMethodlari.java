package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class C01_DriverMethodlari {
    public static void main(String[] args) {

        // Driver i kullanabilmek icin ilk once ilgili driver in bilgisayarimizda olmasi gerekir.
        // Eger driver bilgisayarimizda yoksa WebDriverManager kütüphanesini kullanarak
        // driver i projemize ekleyebiliriz.
        WebDriverManager.chromedriver().setup();

        // Driver i kullanabilmek icin ilk once bir driver olusturmamiz gerekir.
        // Driver olusturmak icin WebDriver arayuzunden ChromeDriver sinifini kullaniriz.
        WebDriver driver = new ChromeDriver();

        // Driver i kullanarak bir web sayfasina gitmek icin get() methodunu kullaniriz.
        driver.get("https://www.amazon.com.tr");

        // Ekrani maximize yapmak icin manage().window().maximize() methodunu kullaniriz.
        driver.manage().window().maximize();

        // Driver in o anki sayfa basligini almak icin getTitle() methodunu kullaniriz.
        System.out.println("Sayfa Basligi : " + driver.getTitle());

        // Driver in o anki sayfa URL sini almak icin getCurrentUrl() methodunu kullaniriz.
        System.out.println("Sayfa URL : " + driver.getCurrentUrl());

        // Driver in o anki sayfa kaynak kodlarini almak icin getPageSource() methodunu kullaniriz.
        //System.out.println("Sayfa Kaynagi : " + driver.getPageSource());

        // kaynak kodlarin icinde gateway kelimesinin olup olmadigini kontrol edelim.
        String kaynakKod = driver.getPageSource();
        String arananKelime = "gateway";
        if (kaynakKod.contains(arananKelime)) {
            System.out.println("Kaynak kodlarinda " + arananKelime + " kelimesi vardir.");
        } else {
            System.out.println("Kaynak kodlarinda " + arananKelime + " kelimesi yoktur.");
        }

        // sayfanin window handle degerini alalim
        // window handle: her sayfaya ozel olarak verilen unique hash kodudur.
        System.out.println("Sayfa Handle Degeri : " + driver.getWindowHandle());

        // Driver i kapatmak icin close() methodunu kullaniriz.
        driver.close();

    }
}
