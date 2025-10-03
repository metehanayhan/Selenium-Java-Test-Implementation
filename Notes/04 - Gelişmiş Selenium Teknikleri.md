# Gelişmiş Selenium Teknikleri

Bu bölüm, otomasyon kodlarımızı daha profesyonel, yeniden kullanılabilir ve stabil hale getiren teknikleri içerir. `TestBase` sınıfları ile kod tekrarını önler, `Actions` sınıfı ile karmaşık kullanıcı etkileşimlerini simüle eder ve `Faker` sınıfı ile dinamik test verileri üretiriz.

---

### **1. TestBase Sınıfları (TestBaseBeforeAfter & TestBaseBeforeClassAfterClass)**

Şu ana kadar `@Before`, `@After` gibi annotation'ları her test sınıfı (class) içine yazdık. Ancak bir projede onlarca, hatta yüzlerce test sınıfı olabilir. Her birine aynı `driver` kurulum ve kapatma kodlarını kopyala-yapıştır yapmak, yönetilemez bir kabusa dönüşür.

- **Sorun:** Kod tekrarı. Bir değişiklik yapmak istediğimizde (örneğin, bekleme süresini 10 saniyeden 15 saniyeye çıkarmak), tüm sınıfları tek tek gezmemiz gerekir.
- **Çözüm:** `TestBase` adı verilen, ortak hazırlık (`@Before`) ve temizlik (`@After`) metotlarını içeren **üst (parent)** bir sınıf oluşturmak. Diğer test sınıflarımızı bu `TestBase` sınıfından **miras alır (extends)**.
- **Nasıl Çalışır? (Inheritance - Kalıtım):**
    1. `TestBase` adında `abstract` bir sınıf oluşturulur. Bu sınıfın içine `@Before` ve `@After` (veya `@BeforeClass` / `@AfterClass`) metotları konulur. `driver` objesi de burada `protected` olarak tanımlanır.
    2. Asıl testlerimizi yazdığımız `GoogleAramaTesti` gibi sınıflar, `extends TestBase` ifadesiyle bu sınıftan miras alır.
    3. Artık `GoogleAramaTesti` sınıfı, `TestBase` içindeki tüm metotlara ve `driver` objesine sanki kendi içindeymiş gibi erişebilir. Bu sayede, test sınıflarımızda sadece `@Test` metotları kalır ve kod inanılmaz derecede temizlenir.
- **`TestBaseBeforeAfter` vs `TestBaseBeforeClassAfterClass`:**
    - **`TestBaseBeforeAfter`:** `@Before` ve `@After` kullanır. Her test metodu için yeni bir tarayıcı açar ve kapatır. Testlerin birbirinden tamamen bağımsız çalışmasını sağlar, daha güvenilirdir.
    - **`TestBaseBeforeClassAfterClass`:** `@BeforeClass` ve `@AfterClass` kullanır. Sınıftaki tüm testler için sadece bir kez tarayıcı açar ve testler bitince kapatır. Daha hızlıdır ancak bir testin durumu diğerini etkileyebilir (riskli).

---

### **2. Actions Sınıfı**

Selenium'daki temel `click()` ve `sendKeys()` metotları, fareyi bir elementin üzerine getirmek (hover), sağ tıklamak, çift tıklamak veya sürükle-bırak gibi karmaşık eylemleri yapamaz. `Actions` sınıfı, bu tür gelişmiş kullanıcı etkileşimlerini bir dizi komut olarak zincirleyerek (chaining) yapmamızı sağlar.

- **Nasıl Kullanılır?**
    1. `Actions` sınıfından bir obje oluşturulur:
        - `Actions actions = new Actions(driver);`
    2. Yapılmak istenen eylemler art arda metotlar halinde çağrılır.
    3. En sonda **mutlaka** `.perform()` metodu çağrılmalıdır. Aksi halde eylemler gerçekleşmez.
- **Sık Kullanılan `Actions` Metotları:**
    - `moveToElement(element)`: Fareyi belirtilen elementin üzerine götürür (hover). Açılır menüler için çok kullanılır.
    - `contextClick(element)`: Elemente sağ tıklar.
    - `doubleClick(element)`: Elemente çift tıklar.
    - `dragAndDrop(sourceElement, targetElement)`: Bir elementi (`source`) tutup başka bir elementin (`target`) üzerine sürükleyip bırakır.
    - `clickAndHold(element)`: Fareyi bir element üzerinde basılı tutar.
    - `release()`: Basılı tutulan fareyi serbest bırakır.
    - `sendKeys(keys)`: O an odaklanılan yere klavye tuşları gönderir.
    - `keyDown(Keys.SHIFT)`: Bir tuşa basılı tutar (örn: Shift).
    - `keyUp(Keys.SHIFT)`: Basılı tutulan tuşu serbest bırakır.
- **Örnek (Hover):**Java
    
    `WebElement menu = driver.findElement(By.id("anaMenu"));
    WebElement altMenu = driver.findElement(By.id("altMenuLink"));
    actions.moveToElement(menu).moveToElement(altMenu).click().perform();`
    

---

### **. Faker Sınıfı**

Testlerimizde genellikle sahte (fake) ama gerçekçi görünen verilere ihtiyaç duyarız: kullanıcı adı, şifre, adres, e-posta, telefon numarası vb. Bu verileri her seferinde elle yazmak yerine, `JavaFaker` gibi kütüphanelerle dinamik olarak üretebiliriz.

- **Ne İşe Yarar?**
    - Her test çalıştığında farklı ve rastgele veriler üreterek testin kapsamını genişletir.
    - Testlerimizi daha gerçekçi hale getirir.
    - Veri hazırlama zahmetini ortadan kaldırır.
- **Nasıl Kullanılır?**
    1. Projenin `pom.xml` dosyasına `JavaFaker` dependency'si eklenir.
    2. `Faker` sınıfından bir obje oluşturulur:
        - `Faker faker = new Faker();`
    3. Bu obje üzerinden yüzlerce farklı türde veri üretilebilir:
        - `String isim = faker.name().firstName();`
        - `String email = faker.internet().emailAddress();`
        - `String sifre = faker.internet().password(8, 16);`
        - `String sehir = faker.address().city();`

---

### **4. Senkronizasyon (Synchronization) - Wait (Bekleme)**

Otomasyon testlerinin en sık başarısız olma nedenlerinden biri **senkronizasyon problemleridir**. Kodumuz, web sayfasından daha hızlı çalışır. `driver.findElement()` komutu çalıştığı anda, aradığı element henüz sayfada yüklenmemiş olabilir. Bu durumda `NoSuchElementException` hatası alırız ve testimiz kalır.

- **Çözüm:** Kodumuza "bekleme" mekanizmaları ekleyerek, web sayfasının hızına uyum sağlamasını söyleriz.
- **Bekleme Stratejileri:**
    1. **`Thread.sleep(5000)` (Hard Wait): KESİNLİKLE KULLANILMAMALIDIR!**
        - **Neden Kötü?** Kodu, ne olursa olsun belirtilen süre (örn: 5 saniye) kadar körü körüne bekletir. Element 1 saniyede gelse bile, kalan 4 saniyeyi boşa harcar. Element 6 saniyede gelirse, yine de hata verir. Testleri yavaşlatır ve güvenilmez yapar.
    2. **`implicitlyWait` (Örtülü Bekleme):**
        - **Açıklama:** Daha önce `manage()` metotlarında gördük. Testin en başında bir kere ayarlanır (`driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));`). `findElement` komutu, elementi bulamazsa, belirtilen süre kadar (10sn) elementin DOM'a (HTML kodlarına) yüklenmesini bekler.
        - **Avantajı:** Kurulumu kolaydır, birçok senkronizasyon sorununu çözer.
        - **Dezavantajı:** Sadece elementin DOM'da var olmasını bekler. Elementin "tıklanabilir" veya "görünür" olmasını beklemez. Bu gibi durumlar için yetersiz kalabilir.
    3. **`ExplicitlyWait` (Açık Bekleme): EN GÜVENİLİR YÖNTEM!**
        - **Açıklama:** Belirli bir element için, belirli bir koşul gerçekleşene kadar beklemesini söylediğimiz, daha akıllı ve esnek bir bekleme türüdür.
        - **Nasıl Kullanılır?** `WebDriverWait` sınıfı ve `ExpectedConditions` sınıfı birlikte kullanılır.
            - `WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));`
            - `wait.until(ExpectedConditions.koşul(element));`
        - **Sık Kullanılan `ExpectedConditions` Koşulları:**
            - `visibilityOf(element)`: Element görünür olana kadar bekle.
            - `elementToBeClickable(locator)`: Element tıklanabilir olana kadar bekle (en sık kullanılanlardan).
            - `alertIsPresent()`: Bir uyarı penceresi çıkana kadar bekle.
            - `textToBePresentInElement(element, "metin")`: Elementin içinde belirtilen metin görünene kadar bekle.

---