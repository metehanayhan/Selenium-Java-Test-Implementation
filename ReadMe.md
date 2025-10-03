\# Java-Selenium-Automation-Journey





Bu repo, \*\*Türksat Test Birimi\*\*'nde gerçekleştirdiğim uzun dönem stajım boyunca edindiğim bilgi ve tecrübeleri içermektedir. Selenium WebDriver'ı Java programlama dili ile kullanarak web otomasyonu alanında yaptığım pratik çalışmaları, ders notlarını ve örnek kodları kapsamaktadır.



Amacım, öğrenme sürecimi belgelemek ve bu alanda kendini geliştirmek isteyenler için bir kaynak oluşturmaktır.



\##  Eğitim Referansı



Bu repoda yer alan notlar ve kod pratikleri, aşağıda linki bulunan kapsamlı Udemy kursu takip edilerek oluşturulmuştur.



\* \*\*Kurs Adı:\*\* \[Selenium WebDriver ile Test Otomasyonu (JUnit,TestNG,BDD)](https://www.udemy.com/course/selenium-ile-test-otomasyonuj-unit-test-ng-cucumber-bdd/)



\##  Repo İçeriği



Bu depoda aşağıdaki konu başlıkları altında toplanmış pratik kodlar ve ders notları bulunmaktadır:



\* \*\*Selenium WebDriver Temelleri\*\*

&nbsp;   \* `Driver`, `Maps`, `Manage` Metotları

&nbsp;   \* Temel ve İleri Seviye `Locators` (XPath, CSS Selector)

\* \*\*Test Frameworkleri ve Yapıları\*\*

&nbsp;   \* `JUnit` ve `TestNG` Annotation'ları (@Test, @Before, @After vb.)

&nbsp;   \* `Assertions` ile Test Doğrulama

\* \*\*Web Elementleri ile Etkileşim\*\*

&nbsp;   \* `DropDown`, `CheckBox`, `RadioButton`

&nbsp;   \* `IFrame`, `Alerts` ve `WindowHandles` (Pencere/Sekme) Yönetimi

\* \*\*Gelişmiş Selenium Teknikleri\*\*

&nbsp;   \* `Actions` Sınıfı (Sağ tık, sürükle-bırak vb.)

&nbsp;   \* `Faker` Sınıfı ile Dinamik Test Verisi Üretme

&nbsp;   \* `Wait` (Bekleme) Stratejileri: Implicitly \& Explicitly Wait

&nbsp;   \* `TestBase` ile Kod Tekrarını Önleme

\* \*\*Harici Dosyalarla Çalışma\*\*

&nbsp;   \* Dosya İndirme, Yükleme ve Varlığını Kontrol Etme

&nbsp;   \* `Apache POI` Kütüphanesi ile Excel Dosyalarını Okuma ve Yazma

\* \*\*İleri Seviye Konseptler ve Mimariler\*\*

&nbsp;   \* \*\*Page Object Model (POM)\*\* Tasarım Deseni

&nbsp;   \* `Singleton Driver` Yapısı

&nbsp;   \* `DataProvider` ile Veri Odaklı Test (Data-Driven Testing)

&nbsp;   \* XML Dosyaları ile Test Yönetimi ve Paralel Çalıştırma



\##  Proje Yapısı



Kodlar, öğrenme sürecine paralel olarak ders bazında paketlenmiştir. Tüm test sınıfları ve yardımcı paketler, Maven standartlarına uygun olarak `src/test/java` dizini altında yer almaktadır.



```

src

└── test

&nbsp;   └── java

&nbsp;       ├── Ders\_01\_WebDriverTemelleri

&nbsp;       ├── Ders\_02\_JUnit

&nbsp;       ├── Ders\_03\_WebElementExceptionlari

&nbsp;       ├── ...

&nbsp;       └── utilities

&nbsp;           ├── Driver.java

&nbsp;           └── TestBase.java

```



