package Ders_01;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C07_XPath {
    public static void main(String[] args) throws InterruptedException {

        //chrome driver i setup edelim
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
        WebElement deleteButton = driver.findElement(org.openqa.selenium.By.xpath("//*[text()='Delete']"));
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
