# Yardımcı Fonksiyonlar ve Teknikler (Utilities)

Bu bölüm, doğrudan bir test senaryosu olmasa da, testlerimizin kalitesini, güvenilirliğini ve bakımını kolaylaştıran çok önemli yardımcı araçları ve teknikleri içerir. Bu metotlar genellikle projenin bir "Utilities" veya "Helpers" paketinde toplanır ve tüm test sınıfları tarafından kullanılır.

---

### **1. Ekran Görüntüsü Alma (Taking a Full Page Screenshot)**

- **Neden Gerekli?** Bir test başarısız olduğunda, hatanın tam olarak hangi anda ve ekranın ne durumdayken oluştuğunu anlamak paha biçilmezdir. Ekran görüntüsü, hatanın görsel bir kanıtıdır ve hata ayıklama (debugging) sürecini inanılmaz hızlandırır. Genellikle `@After` metodunda, test başarısız olursa (`ITestResult` ile kontrol edilerek) ekran görüntüsü alınacak şekilde ayarlanır.
- **Nasıl Yapılır? (`TakesScreenshot` Arayüzü):**
    1. `driver` objesi, `TakesScreenshot` arayüzüne cast edilir (dönüştürülür).
        - `TakesScreenshot ts = (TakesScreenshot) driver;`
    2. `.getScreenshotAs()` metodu çağrılarak ekran görüntüsü bir dosya (`File`) objesi olarak alınır.
        - `File tumSayfaResim = ts.getScreenshotAs(OutputType.FILE);`
    3. Bu geçici dosya, projemizde istediğimiz kalıcı bir yere (genellikle proje altında bir "screenshots" klasörüne) Java'nın `FileUtils.copyFile()` metodu ile kopyalanır.
    4. **İpucu:** Dosya ismini dinamik olarak o anki tarih ve saat bilgisiyle oluşturmak (`"screenshot_20251003_121530.png"` gibi), her ekran görüntüsünün benzersiz olmasını ve eskilerin üzerine yazılmamasını sağlar.

---

### **2. Belirli Bir Web Elementinin Ekran Görüntüsünü Alma (Element Screenshot)**

- **Neden Gerekli?** Bazen tüm sayfanın değil, sadece belirli bir elementin (örneğin bir hata mesajı, bir grafik veya bir ürün resmi) görüntüsünü almak yeterlidir. Bu, daha odaklı bir kanıt sunar.
- **Nasıl Yapılır?**
    - Selenium 4 ile birlikte bu özellik doğrudan `WebElement`'e geldi.
    1. Görüntüsü alınacak `WebElement` bulunur.
        - `WebElement logo = driver.findElement(By.id("logo"));`
    2. Bu element üzerinden `.getScreenshotAs()` metodu çağrılır.
        - `File logoResmi = logo.getScreenshotAs(OutputType.FILE);`
    3. Yine `FileUtils.copyFile()` ile bu dosya istenilen yere kaydedilir.

---

### **3. JavaScript ile Scroll ve Click (JavaScriptExecutor)**

- **Neden Gerekli?** Bazen Selenium'un standart `click()` metodu çalışmaz. Bunun sebepleri şunlar olabilir:
    - Element başka bir elementin arkasında kalmıştır (intercepted).
    - Element sayfanın görünür alanının dışındadır ve `click()` yapmadan önce ona scroll (kaydırma) yapmak gerekir.
    - Standart metotların tetiklemediği özel JavaScript olayları (events) vardır.
- **Çözüm:** `JavascriptExecutor` arayüzünü kullanarak tarayıcıda doğrudan JavaScript kodları çalıştırmak. Selenium'un yapamadığı yerde JavaScript devreye girer.
- **Nasıl Kullanılır?**
    1. `driver` objesi `JavascriptExecutor` arayüzüne cast edilir.
        - `JavascriptExecutor js = (JavascriptExecutor) driver;`
    2. `.executeScript()` metodu ile JavaScript kodu çalıştırılır.
- **Örnek Senaryolar:**
    - **Elemente Scroll Yapma (Kaydırma):**
        - `js.executeScript("arguments[0].scrollIntoView(true);", element);`
        - Bu komut, belirtilen `element` görünür olana kadar sayfayı kaydırır.
    - **JavaScript ile Click Yapma (Tıklama):**
        - `js.executeScript("arguments[0].click();", element);`
        - Bu komut, Selenium'un `click()` metodundan daha direkt çalışır ve genellikle engelleri aşar.
    - **Sayfanın En Altına Gitme:**
        - `js.executeScript("window.scrollTo(0, document.body.scrollHeight)");`

---

### **4. Yeniden Kullanılabilir Metotlar Oluşturma (Reusable Methods)**

Bu konu, daha önce bahsettiğimiz `TestBase` mantığının bir uzantısıdır ve profesyonel otomasyonun temelidir. Ekran görüntüsü alma veya pencere değiştirme gibi işlemleri her seferinde sıfırdan yazmak yerine, bunları parametre alan, kendi işini yapıp sonucu döndüren genel metotlar haline getiririz.

- **Avantajları:**
    - **DRY (Don't Repeat Yourself):** Kod tekrarını önler.
    - **Bakım Kolaylığı:** Bir mantığı değiştirmek istediğinizde sadece tek bir metodu güncellemeniz yeterlidir.
    - **Okunabilirlik:** Test kodları çok daha sade ve anlaşılır hale gelir. `takeScreenshot("login_error")` gibi bir metot, 5-6 satırlık teknik koddan çok daha açıklayıcıdır.
- **`Reusable Method: Screenshot` Örneği:**
    - String olarak bir dosya adı alan (`takeScreenshot(String fileName)`) bir metot yazılır.
    - Bu metot içinde yukarıda anlattığımız tüm ekran görüntüsü alma adımları (cast etme, dosya oluşturma, kopyalama, tarih ekleme) yapılır.
    - Artık testin herhangi bir yerinde `ReusableMethods.takeScreenshot("urun_sayfasi");` diyerek kolayca ekran görüntüsü alınabilir.
- **`Reusable Method: WindowHandles` Örneği:**
    - Bölüm 3'te gördüğümüz yeni sekmeye geçiş mantığı (ana sayfa handle'ını al, tüm handle'ları al, döngü kur, geçiş yap) karmaşık ve uzundur.
    - Bu mantık, gidilecek sayfanın başlığını (title) parametre olarak alan (`switchToWindowByTitle(String targetTitle)`) bir metoda dönüştürülebilir.
    - Metot, tüm pencereleri gezer, başlığı `targetTitle` ile eşleşen pencereye `switchTo()` yapar.
    - Test kodunda artık `ReusableMethods.switchToWindowByTitle("Ürün Detay Sayfası");` demek yeterli olacaktır.

---