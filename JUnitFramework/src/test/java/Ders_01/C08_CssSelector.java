package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C08_CssSelector {
    public static void main(String[] args) throws InterruptedException {
        /*
        * css selectorde sadece id ve class attribute'leri için locater oluşturabiliriz
        * id için # isareti kullanılır
        * class için . isareti kullanılır
        * id'si firstname olan bir webelement için css selector : #firstname
        * class'ı inputtext olan bir webelement için css selector : .inputtext
        * */

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // https://the-internet.herokuapp.com/add_remove_elements/ adresine gidelim
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        Thread.sleep(2000); // 2 saniye bekle
        // Add Element butonuna basalım
        WebElement addElementButton = driver.findElement(org.openqa.selenium.By.xpath("//button[@onclick='addElement()']"));
        addElementButton.click();
        Thread.sleep(2000);

        // Delete butonu'nun gorunur oldugunu test edelim
        WebElement deleteButton = driver.findElement(org.openqa.selenium.By.cssSelector(".added-manually"));
        if (deleteButton.isDisplayed()) {
            System.out.println("Delete button is visible. Test PASSED");
        } else {
            System.out.println("Delete button is not visible. Test FAILED");
        }

        Thread.sleep(2000);
        // Delete tusuna basalim
        deleteButton.click();

        // Add/Remove Elements yazisinin gorunur oldugunu test edelim
        WebElement addRemoveElementsText = driver.findElement(org.openqa.selenium.By.xpath("//h3"));
        if (addRemoveElementsText.isDisplayed()) {
            System.out.println("Add/Remove Elements text is visible. Test PASSED");
        } else {
            System.out.println("Add/Remove Elements text is not visible. Test FAILED");
        }

        Thread.sleep(2000);
        // sayfayi kapatalim
        driver.close();
    }
}
