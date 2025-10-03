# Test Çerçevesi (Framework) ve Annotation'lar

Bir test otomasyonu "framework"ü (çerçevesi), bize testlerimizi yazmak, organize etmek, çalıştırmak ve raporlamak için belirli bir yapı ve kurallar bütünü sunar. Sadece art arda komutlar yazmak yerine, testlerimizi metotlara böler, bu metotların ne zaman çalışacağını belirler ve en önemlisi, testin sonucunun "BAŞARILI" mı yoksa "BAŞARISIZ" mı olduğunu belirlememizi sağlar. En popüler Java test framework'leri **JUnit** ve **TestNG**'dir. Konseptler büyük ölçüde aynıdır.

---

### **1. JUnit - Test Annotation'ları (Annotations)**

Annotation'lar (İşaretlemeler), önüne konuldukları Java metotlarına özel anlamlar katan `@` sembolü ile başlayan etiketlerdir. Bu etiketler sayesinde JUnit veya TestNG, bir metodun basit bir Java metodu mu, yoksa bir test metodu mu, yoksa bir hazırlık metodu mu olduğunu anlar.

- **`@Test` Annotation'ı:**
    - **Açıklama:** Bu, en temel ve en önemli annotation'dır. Bir metodun başına `@Test` koyduğunuzda, o metodun artık bir **test senaryosu (test case)** olduğunu framework'e bildirmiş olursunuz. Framework, testleri çalıştırdığında sadece bu işarete sahip metotları bulur ve yürütür.
    - **Kurallar:** `@Test` metotları genellikle `public void` olarak tanımlanır ve parametre almazlar.
    - **Örnek:**
        
        ```jsx
        import org.junit.Test;
        
        public class GoogleTest {
            @Test
            public void test01_GoogleTitleTest() {
                // Bu bir test senaryosudur.
                // Selenium kodları buraya yazılır.
                System.out.println("Google başlık testi çalıştı.");
            }
        
            public void buBirTestMethoduDegil() {
                // Bu metodun başında @Test olmadığı için
                // framework tarafından çalıştırılmaz.
            }
        }
        ```
        

---

### **2. Test Yaşam Döngüsü: `@Before`, `@After`, `@BeforeClass`, `@AfterClass`**

Test senaryolarında sürekli tekrar eden adımlar vardır: tarayıcıyı aç, ayarları yap, teste başla, test bitince tarayıcıyı kapat. Bu tekrar eden kodları her `@Test` metodunun içine yazmak hem kod tekrarına (DRY - Don't Repeat Yourself prensibine aykırı) yol açar hem de bakımı zorlaştırır. İşte bu "yaşam döngüsü" (lifecycle) annotation'ları bu sorunu çözer.

*(Not: JUnit 5 ve TestNG'de isimler değişebilir (`@BeforeEach`, `@AfterAll` vb.) ama çalışma mantığı aynıdır.)*

- **`@Before` (Her Testten Önce):**
    - **Açıklama:** Bu annotation'a sahip metot, sınıf içindeki **her bir `@Test` metodundan hemen önce** çalışır. Her test için sıfırdan, temiz bir başlangıç yapmak için idealdir.
    - **Kullanım Alanı:** `WebDriver` objesini oluşturmak (`driver = new ChromeDriver();`), pencereyi maximize etmek, `implicitlyWait` atamak gibi her testin başında yapılması gereken standart hazırlıklar.
- **`@After` (Her Testten Sonra):**
    - **Açıklama:** Bu annotation'a sahip metot, sınıf içindeki **her bir `@Test` metodu bittikten hemen sonra** (başarılı da olsa, başarısız da olsa) çalışır.
    - **Kullanım Alanı:** Her test sonrası tarayıcıyı kapatmak (`driver.close();`), test başarısız olduysa ekran görüntüsü almak gibi temizlik işlemleri.
- **`@BeforeClass` (Tüm Sınıftan Önce Sadece Bir Kez):**
    - **Açıklama:** Bu annotation'a sahip metot, sınıftaki **tüm `@Test`'ler çalışmaya başlamadan önce, yalnızca bir kez** çalışır.
    - **Kullanım Alanı:** Sadece bir kez yapılması gereken ve maliyetli olan kurulumlar için kullanılır. Örneğin, bir veritabanı bağlantısı kurmak.
    - **Kural:** Bu metoda sahip metotlar **`static`** olmak zorundadır.
- **`@AfterClass` (Tüm Sınıftan Sonra Sadece Bir Kez):**
    - **Açıklama:** Bu annotation'a sahip metot, sınıftaki **tüm `@Test`'ler bittikten sonra, yalnızca bir kez** çalışır.
    - **Kullanım Alanı:** `@BeforeClass`'ta yapılan kurulumları geri almak/kapatmak için kullanılır. Veritabanı bağlantısını kapatmak, `driver.quit()` ile session'ı tamamen sonlandırmak gibi.
    - **Kural:** Bu metoda sahip metotlar da **`static`** olmak zorundadır.
- **Çalışma Sırası Örneği:**
    
    ```jsx
    public class SampleTestLifecycle {
        @BeforeClass
        public static void setupClass() { System.out.println("-> @BeforeClass: Sadece 1 kez çalıştı."); }
    
        @Before
        public void setup() { System.out.println("--> @Before: Testten önce çalıştı."); }
    
        @Test
        public void test01() { System.out.println("-----> TEST 1 ÇALIŞIYOR"); }
    
        @Test
        public void test02() { System.out.println("-----> TEST 2 ÇALIŞIYOR"); }
    
        @After
        public void tearDown() { System.out.println("<-- @After: Testten sonra çalıştı."); }
    
        @AfterClass
        public static void tearDownClass() { System.out.println("<- @AfterClass: Sadece 1 kez çalıştı."); }
    }
    ```
    
    - **Çıktısı Şöyle Olurdu:**
        
        ```jsx
        > @BeforeClass: Sadece 1 kez çalıştı.
        --> @Before: Testten önce çalıştı.
        -----> TEST 1 ÇALIŞIYOR
        <-- @After: Testten sonra çalıştı.
        --> @Before: Testten önce çalıştı.
        -----> TEST 2 ÇALIŞIYOR
        <-- @After: Testten sonra çalıştı.
        <- @AfterClass: Sadece 1 kez çalıştı.
        ```
        

---

### **3. Assertions (Doğrulamalar)**

Bir testi "test" yapan şey, içindeki **doğrulama (assertion)** adımıdır. Assertion olmadan yazılan bir otomasyon kodu, sadece tarayıcıda bir dizi eylemi gerçekleştiren bir "script"tir. Testin geçip kaldığını belirleyemez.

- **Ne İşe Yarar?** Assertion, "beklenen sonuç" ile "gerçekleşen sonuç" arasında bir karşılaştırma yapar.
    - Eğer karşılaştırma **doğruysa** (beklenen ile gerçekleşen aynıysa), test o adımdan sessizce geçer ve devam eder.
    - Eğer karşılaştırma **yanlışsa**, test o anda durur, bir `AssertionError` fırlatır ve testi **"BAŞARISIZ (FAILED)"** olarak işaretler.
- **En Sık Kullanılan Assertion Metotları (JUnit):**
    - `assertEquals(expected, actual)`: En çok kullanılanıdır. İki değerin birbirine eşit olup olmadığını kontrol eder.
        - **Örnek:**Java
            
            ```jsx
            String beklenenBaslik = "Google";
            String gerceklesenBaslik = driver.getTitle();
            assertEquals("Hata: Sayfa başlığı beklenenden farklı!", beklenenBaslik, gerceklesenBaslik);
            ```
            
            *(İlk parametre olan mesaj, test başarısız olduğunda konsolda görünecek açıklamadır ve hata ayıklamayı çok kolaylaştırır.)*
            
    - `assertTrue(condition)`: Bir koşulun `true` olup olmadığını kontrol eder.
        - **Örnek:** `assertTrue("Hata: Başlık 'Google' kelimesini içermiyor!", driver.getTitle().contains("Google"));`
    - `assertFalse(condition)`: Bir koşulun `false` olup olmadığını kontrol eder.
        - **Örnek:** `assertFalse("Hata: Arama kutusu hala aktif olmamalı!", searchBox.isEnabled());`
    - `assertNotNull(object)`: Bir objenin `null` olmadığını doğrular.

---