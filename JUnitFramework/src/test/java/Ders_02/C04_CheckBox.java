package Ders_02;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class C04_CheckBox {

    WebDriver driver;   // testten de erişebilmesi için global yaptık
    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown(){
        driver.quit();  // her bir sekme kapansın diye quit yaptık.. close sadece en son sekmeyi kapatır
    }

    //tasks
    // https://the-internet.herokuapp.com/checkboxes adresine gidelim
    // checkbox1 ve checkbox2 elementlerini locate edelim
    // checkbox1 secili degilse onay kutusunu tıklayalım
    // checkbox2 secili degilse onay kutusunu tıklayalım

    @Test
    public void checkbox() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        Thread.sleep(2000);
        WebElement checkbox1 = driver.findElement(org.openqa.selenium.By.xpath("(//*[@type='checkbox'])[1]"));
        WebElement checkbox2 = driver.findElement(org.openqa.selenium.By.xpath("(//*[@type='checkbox'])[2]"));
        if (!checkbox1.isSelected()){
            Thread.sleep(2000);
            checkbox1.click();
        }
        if (checkbox2.isSelected()) {
            Thread.sleep(2000);
            checkbox2.click();
        }

    }

}
