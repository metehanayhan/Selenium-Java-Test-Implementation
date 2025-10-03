# Selenium WebDriver Temelleri

### **1. Driver Methodları (Driver Methods)**

`WebDriver` objesi (genellikle `driver` olarak adlandırılır), bizim tarayıcı ile iletişim kuran kumandamızdır. Bu objenin metotları, tarayıcının kendisine yönelik genel komutları içerir.

- **Ne İşe Yarar?** Tarayıcıyı başlatmak, belirli bir URL'ye gitmek, sayfa bilgilerini almak ve tarayıcıyı kapatmak gibi en temel işlemleri yapmamızı sağlar.
- **En Sık Kullanılan Driver Metotları:**
    - `driver.get("URL")`:
        - **Açıklama:** String olarak verilen URL'e gider. Tarayıcının adresi çubuğuna adresi yazıp Enter'a basmakla aynı işi yapar. Bu komut, sayfanın tamamen yüklenmesini bekler.
        - **Örnek:** `driver.get("https://www.google.com");`
    - `driver.getTitle()`:
        - **Açıklama:** Açık olan sayfanın başlığını (`<title>` etiketi içindeki metni) String olarak döndürür. Testlerde doğrulama (assertion) yapmak için çok sık kullanılır.
        - **Örnek:** `String sayfaBasligi = driver.getTitle();`
    - `driver.getCurrentUrl()`:
        - **Açıklama:** Tarayıcının adres çubuğunda o an yazan URL'i String olarak döndürür. Özellikle bir butona tıkladıktan sonra doğru sayfaya yönlendirilip yönlendirilmediğimizi kontrol etmek için idealdir.
        - **Örnek:** `String mevcutUrl = driver.getCurrentUrl();`
    - `driver.getPageSource()`:
        - **Açıklama:** Sayfanın kaynak kodlarını (HTML kodlarını) String olarak döndürür. Genellikle belirli bir metnin veya elementin kaynak kodda olup olmadığını kontrol etmek için kullanılır.
        - **Örnek:** `String kaynakKodlar = driver.getPageSource();`
    - `driver.close()` vs `driver.quit()`:
        - **`close()`:** Sadece o an `driver`'ın kontrol ettiği tarayıcı penceresini/sekmesini kapatır. Eğer birden fazla pencere açıksa, diğerleri açık kalır.
        - **`quit()`:** WebDriver session'ını tamamen sonlandırır. `driver` tarafından açılmış **tüm** pencereleri kapatır ve arka plandaki `chromedriver.exe` veya `geckodriver.exe` gibi sürücü proseslerini sonlandırır. **Testlerin sonunda mutlaka `quit()` kullanılmalıdır.**

---

### **2. Navigate Methodları (Navigation Methods)**

`Maps()` metotları, tarayıcının geçmişiyle (history) ilgili işlemleri simüle eder. Kullanıcının tarayıcıdaki ileri, geri ve yenile butonlarına basması gibi düşünülebilir.

- **Ne İşe Yarar?** Test senaryolarında sayfalar arasında gezinmeyi, bir önceki sayfaya dönmeyi veya sayfayı tazelemeyi sağlar.
- **En Sık Kullanılan Navigate Metotları:**
    - `driver.navigate().to("URL")`:
        - **Açıklama:** `driver.get()` ile aynı işi yapar, yani belirtilen URL'e gider. Aralarındaki temel fark, `get()` sayfanın tam yüklenmesini beklerken, `to()` beklemez. Genellikle `get()` tercih edilir.
        - **Örnek:** `driver.navigate().to("https://www.google.com");`
    - `driver.navigate().back()`:
        - **Açıklama:** Tarayıcı geçmişinde bir önceki sayfaya döner.
        - **Örnek:** `driver.navigate().back();`
    - `driver.navigate().forward()`:
        - **Açıklama:** Tarayıcı geçmişinde bir sonraki sayfaya gider.
        - **Örnek:** `driver.navigate().forward();`
    - `driver.navigate().refresh()`:
        - **Açıklama:** Mevcut sayfayı yeniler (F5 tuşuna basmak gibi).
        - **Örnek:** `driver.navigate().refresh();`

---

### **3. Manage Methodları (Manage Methods)**

`manage()` metotları, tarayıcının ayarları ve yönetimi ile ilgili işlemleri içerir. Pencere boyutları, çerezler (cookies) ve bekleme süreleri (timeouts) gibi konuları yönetiriz.

- **Ne İşe Yarar?** Testin daha stabil çalışması için tarayıcı penceresini yönetmek ve özellikle "bekleme" mekanizmalarını yapılandırmak için kullanılır.
- **En Sık Kullanılan Manage Metotları:**
    - `driver.manage().window()`: Tarayıcı penceresini yönetir.
        - `.maximize()`: Pencereyi tam ekran yapar. Testlerin başında genellikle ilk yapılan işlemdir.
        - `.fullscreen()`: Pencereyi F11'e basılmış gibi tam ekran moduna alır.
        - `.setSize(new Dimension(width, height))`: Pencereyi belirtilen genişlik ve yükseklik değerlerine getirir. (Responsive tasarım testleri için kullanışlıdır).
        - `.getPosition()` / `.setPosition(new Point(x, y))`: Pencerenin konumunu alır veya ayarlar.
    - `driver.manage().timeouts()`: Bekleme sürelerini yönetir.
        - `.implicitlyWait(Duration.ofSeconds(10))`: **ÇOK ÖNEMLİ!** Bu komut, `driver`'a global bir bekleme süresi tanımlar. `driver`, `findElement` veya `findElements` komutlarıyla bir elementi ararken, bulamadığı anda hemen hata fırlatmak yerine, belirttiğimiz süre kadar (örneğin 10 saniye) elementin DOM'a (HTML kodlarına) gelmesini bekler. Bu süre içinde element bulunursa devam eder, bulunamazsa sürenin sonunda `NoSuchElementException` hatası verir. Testlerin daha stabil çalışmasını sağlar ve genellikle testin başında bir kez ayarlanır.

---

### **4. Locators (Bulucular): `Xpath` ve `css Selector`**

Selenium'un en temel yeteneği, web sayfasındaki HTML elementlerini (buton, yazı alanı, link vb.) bulmaktır. Bu elementleri bulmak için kullandığımız adreslere **Locator** denir.

- **Neden Önemli?** Bir butona tıklamak, bir alana yazı yazmak veya bir metni okumak için önce o elementi "hedeflememiz" gerekir. Locator'lar bu hedeflemeyi yapar. Yanlış veya dayanıksız (brittle) locator'lar testlerin en sık başarısız olma sebebidir.
- **Selenium'daki 8 Temel Locator Stratejisi:**
    1. **`id`**: Genellikle en güvenilir ve en hızlı olanıdır. HTML standardına göre bir `id` sayfada tekil (unique) olmalıdır. **Her zaman ilk tercih olmalıdır.**
        - Örnek: `driver.findElement(By.id("login-button"));`
    2. **`name`**: Genellikle form elementlerinde (`<input>`, `<textarea>`) bulunur. `id` kadar olmasa da güvenilirdir.
        - Örnek: `driver.findElement(By.name("username"));`
    3. **`className`**: Elementin `class` attribute'una göre arama yapar. Bir class birden fazla element tarafından kullanılabileceği için dikkatli olunmalıdır.
        - Örnek: `driver.findElement(By.className("submit-button"));`
    4. **`tagName`**: HTML etiket ismine göre arama yapar (örn: `<a>` tüm linkleri, `<input>` tüm input alanlarını). Genellikle `findElements` ile birlikte kullanılır.
        - Örnek: `List<WebElement> linkler = driver.findElements(By.tagName("a"));`
    5. **`linkText`**: Sadece `<a>` (link) etiketleri için çalışır ve linkin görünen metninin tamamı eşleşmelidir.
        - Örnek: `driver.findElement(By.linkText("Hesabım"));`
    6. **`partialLinkText`**: `linkText`'e benzer ama metnin sadece bir kısmının eşleşmesi yeterlidir.
        - Örnek: `driver.findElement(By.partialLinkText("Hesab"));`
    7. **`xpath`**: En güçlü ve esnek locator'dır. HTML yapısı içinde bir yol (path) tarif ederek elementi bulur. Diğer locator'lar yetersiz kaldığında kullanılır.
        - **Mutlak (Absolute) XPath:** `/html/body/div[1]/div/input` gibi en dış etiketten başlayarak yol tarif eder. **KULLANILMAMALIDIR!** Çünkü sayfadaki en ufak bir yapısal değişiklik (araya bir `<div>` eklenmesi gibi) yolu bozar.
        - **Göreli (Relative) XPath:** `//` ile başlar ve sayfanın herhangi bir yerinden başlayarak arama yapar. **Her zaman bu tercih edilmelidir.**
        - **Temel XPath Yazım Kuralları:**
            - `//tagName[@attribute='value']` -> Örn: `//input[@id='user-name']`
            - Metne göre bulma: `//button[text()='Login']`
            - İçerilen metne göre bulma: `//a[contains(text(), 'Forgot')]`
    8. **`cssSelector`**: XPath'e benzer şekilde güçlü ve esnek bir locator'dır. Genellikle XPath'ten daha hızlı çalışır ve okunabilirliği daha kolaydır.
        - **Temel CSS Selector Yazım Kuralları:**
            - ID ile: `#login-button` (tag name ile: `button#login-button`)
            - Class ile: `.submit-button` (tag name ile: `input.submit-button`)
            - Attribute ile: `[name='username']` (tag name ile: `input[name='username']`
            - İçerilen metin (substring): `a[href*='product']` (`=` contains, `^=` starts-with, `$=` ends-with)

---