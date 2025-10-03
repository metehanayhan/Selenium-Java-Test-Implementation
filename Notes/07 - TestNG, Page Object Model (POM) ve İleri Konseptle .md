# TestNG, Page Object Model (POM) ve İleri Konseptler

Bu bölüm, yazdığımız test script'lerini sağlam, ölçeklenebilir ve yönetilebilir bir otomasyon "framework"üne dönüştürür. TestNG'nin esnekliği ve POM'un getirdiği düzen, büyük projeler için vazgeçilmezdir.

---

### **1. TestNG, POM ve Proje Kurulumu**

- **TestNG Nedir?** "Test Next Generation"ın kısaltmasıdır. JUnit'in üzerine kurulmuş, ondan ilham almış ama çok daha fazla özellik sunan, daha esnek ve güçlü bir test çerçevesidir.
    - **JUnit'e Göre Avantajları:**
        - Testleri daha kolay gruplama ve önceliklendirme.
        - Testleri birbirine bağımlı hale getirebilme (`dependsOnMethods`).
        - Testleri paralel çalıştırabilme yeteneği.
        - `@DataProvider` ile daha kolay veri odaklı test (Data-Driven Testing).
        - Daha detaylı raporlama.
- **Page Object Model (POM) Nedir?** Bu bir kütüphane değil, bir **tasarım desenidir (design pattern)**. Proje kodunu daha organize ve yönetilebilir kılmak için bir mimari sunar.
    - **Temel Fikir:** Web uygulamasının **her bir sayfası için ayrı bir Java sınıfı (class) oluşturulur**.
        - O sayfadaki tüm **locators** (WebElement'ler) ve o elementlerle etkileşime giren **metotlar** bu sınıfta toplanır.
        - Test sınıfları (`@Test` metotlarının olduğu yer) ise asla `driver.findElement()` gibi ham Selenium kodları içermez. Sadece bu "Sayfa Sınıfları"ndan (Page Classes) nesneler oluşturur ve onların metotlarını çağırır.
    - **Faydaları:**
        - **Bakım Kolaylığı:** Bir locator değiştiğinde (örn: login butonunun ID'si), projedeki 100 testi değil, sadece `LoginPage` sınıfındaki tek bir satırı değiştirirsiniz.
        - **Okunabilirlik:** Testler, `loginPage.enterUsername("testuser"); loginPage.clickLoginButton();` gibi, sanki İngilizce bir senaryo okur gibi akıcı ve anlaşılır olur.
        - **Kod Tekrarını Önleme:** Aynı login işlemi 10 farklı testte kullanılıyorsa, bu işlem `LoginPage` sınıfında sadece bir kez yazılır.
- **Kurulum:** Her ikisi de (TestNG ve POM için gerekli Selenium vb. kütüphaneler) projenin `pom.xml` dosyasına `dependency` olarak eklenir.

---

### **2. TestNG ile Test Akışını Yönetme**

- **`priority` (Önceliklendirme):**
    - Normalde TestNG, test metotlarını alfabetik sırayla çalıştırır. Ancak bazen belirli bir sıra isteriz (örn: önce "ürün oluştur" testi, sonra "ürünü düzenle" testi).
    - `@Test` annotation'ına `priority` parametresi ekleyerek çalışma sırasını belirleyebiliriz. Küçük sayı daha önce çalışır.
    - **Örnek:** `@Test(priority = 1)`
- **`dependsOnMethods` (Bağımlılık):**
    - Bir testin çalışabilmesi, başka bir testin başarılı olmasına bağlıysa bu kullanılır.
    - Örneğin, "Sepete Ürün Ekle" testi, "Login Testi"nin başarılı olmasına bağlıdır. Eğer login başarısız olursa, sepet testini çalıştırmanın bir anlamı yoktur.
    - `dependsOnMethods` ile bağlanan test, bağımlı olduğu test başarısız olursa **çalıştırılmaz, "atlandı (skipped)"** olarak işaretlenir. Bu, zaman kazandırır ve hatalı negatif sonuçları önler.
    - **Örnek:** `@Test(dependsOnMethods = "testLogin")`

---

### **3. Hard Assert vs Soft Assert**

- **Hard Assertion (Standart Assert):** Bugüne kadar kullandığımız `Assert.assertEquals()` gibi metotlardır.
    - Doğrulama başarısız olduğu anda `AssertionError` fırlatır, test anında **durur** ve **"BAŞARISIZ"** olarak işaretlenir. Sonraki kod satırları çalıştırılmaz.
- **Soft Assertion (Verification):** TestNG'nin sunduğu harika bir özelliktir.
    - Doğrulama başarısız olsa bile test **durmaz**, çalışmaya devam eder.
    - Tüm doğrulamaları hafızasında biriktirir.
    - Test metodunun en sonunda `softAssert.assertAll()` komutu çağrıldığında, biriken tüm hataları tek seferde raporlar ve test başarısızsa o zaman "BAŞARISIZ" olarak işaretler.
    - **Ne Zaman Kullanılır?** Tek bir testte, birbirine bağlı olmayan birden çok şeyi (ana sayfa başlığı, logosu, footer linki vb.) aynı anda kontrol etmek istediğimizde idealdir. Birinin başarısız olması diğerlerini kontrol etmemize engel olmaz.

---

### **4. POM, Singleton ve Driver Yönetimi**

- **Page Class Yapısı ve Pratiği:**
    1. `pages` adında bir paket (package) oluşturulur.
    2. Uygulamanın `LoginPage`, `HomePage`, `ProductPage` gibi her sayfası için bir Java sınıfı oluşturulur.
    3. Her sayfa sınıfının bir constructor'ı (`public LoginPage()`) olur. Bu constructor içinde `PageFactory.initElements(driver, this);` komutuyla o sayfadaki `@FindBy` ile işaretlenmiş WebElement'lerin başlatılması sağlanır.
    4. Locator'lar, `@FindBy(id = "user-name")` gibi annotation'lar kullanılarak sınıfın en üstünde değişken olarak tanımlanır.
    5. O sayfaya özgü eylemler (`login()`, `searchForProduct(String productName)`) metot olarak yazılır.
- **Singleton Pattern ile Driver Yönetimi:**
    - **Sorun:** Projede birden fazla yerden `new ChromeDriver()` çağrılırsa, birden fazla tarayıcı açılabilir veya `driver` objesi karışabilir.
    - **Çözüm:** `Singleton` tasarım deseni, bir sınıftan proje boyunca **sadece tek bir nesne (instance)** oluşturulmasını garanti eden bir yöntemdir.
    - Genellikle bir `Driver` utility sınıfı oluşturulur. Bu sınıf, `getDriver()` adında bir metot içerir. Bu metot çağrıldığında, `driver` objesi `null` ise (yani daha önce hiç oluşturulmamışsa) `new ChromeDriver()` yapar; eğer `null` değilse (zaten varsa), mevcut olanı geri döndürür. Bu sayede tüm testler aynı `driver` objesini kullanır.

---

### **5. Smoke Test (Duman Testi)**

- **Smoke Test Nedir?** Yazılımın en temel ve en kritik fonksiyonlarının çalışıp çalışmadığını kontrol eden, hızlı ve dar kapsamlı bir test setidir. Amaç, "Bu yazılım daha detaylı testlere girmeden önce ayakta durabiliyor mu, temel işlevleri çalışıyor mu?" sorusuna cevap bulmaktır.
- **Positive Smoke Test:** Sistemin en temel "mutlu yol" (happy path) senaryolarını test eder. Örn: Geçerli kullanıcı adı/şifre ile login olabilme, ana sayfadaki arama kutusuna bir şey yazıp arama yapabilme.
- **Negative Smoke Test:** Sistemin temel hatalı durumlara beklenen doğru tepkileri verip vermediğini kontrol eder. Örn: Geçersiz şifre ile login olmayı deneyince "Hatalı şifre" uyarısı alma.

---

### **6. İleri Seviye TestNG Özellikleri**

- **Extents Report:** TestNG'nin standart raporları basittir. `ExtentReports` gibi harici kütüphanelerle, test adımlarını, ekran görüntülerini, başarı/başarısızlık durumlarını içeren çok daha profesyonel, interaktif ve görsel HTML raporlar oluşturulabilir.
- **`@DataProvider`:** TestNG'nin en güçlü özelliklerinden biridir. Tek bir test metodunu, farklı veri setleriyle tekrar tekrar çalıştırmayı sağlar.
    - **Nasıl Çalışır?** `@DataProvider` annotation'ı ile işaretlenmiş bir metot, iki boyutlu bir `Object` dizisi (`Object[][]`) döndürür. Bu dizinin her bir satırı, test metodu için bir grup parametreyi temsil eder.
    - Test metodu ise `@Test(dataProvider = "dataProviderMetodununAdi")` şeklinde bu veri sağlayıcıya bağlanır.
    - **Örnek:** Login testini 5 farklı kullanıcı adı/şifre çiftiyle çalıştırmak için idealdir.

---