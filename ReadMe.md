# Java-Selenium-Automation-Journey

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-2A9F44?style=for-the-badge)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

##  Hakkında

Bu repo, **Türksatta Test Birimi**'nde gerçekleştirdiğim uzun dönem stajım boyunca edindiğim bilgi ve tecrübeleri içermektedir. Selenium WebDriver'ı Java programlama dili ile kullanarak web otomasyonu alanında yaptığım pratik çalışmaları, ders notlarını ve örnek kodları kapsamaktadır.

Amacım, öğrenme sürecimi belgelemek ve bu alanda kendini geliştirmek isteyenler için bir kaynak oluşturmaktır.

##  Eğitim Referansı

Bu repoda yer alan notlar ve kod pratikleri, aşağıda linki bulunan kapsamlı Udemy kursu takip edilerek oluşturulmuştur.

* **Kurs Adı:** [Selenium WebDriver ile Test Otomasyonu (JUnit,TestNG,BDD)](https://www.udemy.com/course/selenium-ile-test-otomasyonuj-unit-test-ng-cucumber-bdd/)

##  Repo İçeriği

Bu depoda aşağıdaki konu başlıkları altında toplanmış pratik kodlar ve ders notları bulunmaktadır:

* **Selenium WebDriver Temelleri**
    * `Driver`, `Maps`, `Manage` Metotları
    * Temel ve İleri Seviye `Locators` (XPath, CSS Selector)
* **Test Frameworkleri ve Yapıları**
    * `JUnit` ve `TestNG` Annotation'ları (@Test, @Before, @After vb.)
    * `Assertions` ile Test Doğrulama
* **Web Elementleri ile Etkileşim**
    * `DropDown`, `CheckBox`, `RadioButton`
    * `IFrame`, `Alerts` ve `WindowHandles` (Pencere/Sekme) Yönetimi
* **Gelişmiş Selenium Teknikleri**
    * `Actions` Sınıfı (Sağ tık, sürükle-bırak vb.)
    * `Faker` Sınıfı ile Dinamik Test Verisi Üretme
    * `Wait` (Bekleme) Stratejileri: Implicitly & Explicitly Wait
    * `TestBase` ile Kod Tekrarını Önleme
* **Harici Dosyalarla Çalışma**
    * Dosya İndirme, Yükleme ve Varlığını Kontrol Etme
    * `Apache POI` Kütüphanesi ile Excel Dosyalarını Okuma ve Yazma
* **İleri Seviye Konseptler ve Mimariler**
    * **Page Object Model (POM)** Tasarım Deseni
    * `Singleton Driver` Yapısı
    * `DataProvider` ile Veri Odaklı Test (Data-Driven Testing)
    * XML Dosyaları ile Test Yönetimi ve Paralel Çalıştırma

##  Proje Yapısı

Kodlar, öğrenme sürecine paralel olarak ders bazında paketlenmiştir. Tüm test sınıfları ve yardımcı paketler, Maven standartlarına uygun olarak `src/test/java` dizini altında yer almaktadır.

```
src
└── test
    └── java
        ├── Ders_01_WebDriverTemelleri
        ├── Ders_02_JUnit
        ├── Ders_03_WebElementExceptionlari
        ├── ...
        └── utilities
            ├── Driver.java
            └── TestBase.java
```
