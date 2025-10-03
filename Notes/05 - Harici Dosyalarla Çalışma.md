# Harici Dosyalarla Çalışma

Test otomasyonu her zaman sadece web sitesi üzerinde gerçekleşmez. Bazen bir dosyayı indirdiğimizi (download) doğrulamamız, bazen de bir form aracılığıyla sisteme bir dosya yüklememiz (upload) gerekir. En yaygın senaryolardan biri de, test verilerimizi (kullanıcı adları, şifreler, aranacak ürünler vb.) kodun içine gömmek yerine, bir Excel dosyasında saklayıp oradan okumaktır. Bu, testin bakımını kolaylaştırır ve teknik bilgisi olmayan kişilerin bile teste yeni veriler eklemesine olanak tanır.

Bu bölümde **Selenium**'un doğrudan yapamadığı, **Java**'nın kendi dosya sistemi (`File System`) yeteneklerini ve **Apache POI** gibi harici kütüphaneleri kullanacağız.

---

### **1. Dosya Varlığını Kontrol Etme (File Exist) ve İndirme (Download) Testi**

- **Senaryo:** Web sitesindeki "Raporu İndir" butonuna tıkladık. Peki, dosyanın gerçekten bilgisayarımıza inip inmediğini nasıl anlarız?
- **Yaklaşım:** Selenium bir dosyanın indirildiğini doğrudan bilemez, çünkü yetki alanı tarayıcı ile sınırlıdır. Ama Java'yı kullanarak bilgisayarımızdaki belirli bir klasörü kontrol edebiliriz.
    1. Test başlamadan önce, indirme klasöründe aynı isimde bir dosya varsa temizlenir.
    2. Selenium ile butona tıklanır ve dosyanın inmesi için bir süre beklenir (`Thread.sleep()` burada nadiren kabul edilebilir bir kullanımdır, çünkü indirme süresi değişkendir).
    3. Java'nın `Files.exists()` metodu kullanılarak, belirtilen yolda (path) o dosyanın var olup olmadığı kontrol edilir.
    4. Sonuç `assertTrue` ile doğrulanır.
- **Dinamik Dosya Yolu (Path) Oluşturma:**
    - Bir test, farklı bilgisayarlarda çalışabilmelidir. `"C:\Users\Metehan\Downloads\rapor.pdf"` gibi sabit bir dosya yolu kullanmak, testi sadece sizin bilgisayarınıza bağımlı kılar.
    - **Çözüm:** Java ile dinamik olarak her bilgisayara uyum sağlayan yollar oluşturmak.
        - `System.getProperty("user.home")`: O anki kullanıcının ana klasörünü verir (Örn: `"C:\Users\Metehan"`).
        - `System.getProperty("user.dir")`: Projenin çalıştığı ana klasörü verir.
        - **Örnek:** `String dinamikYol = System.getProperty("user.home") + "/Downloads/rapor.pdf";`

---

### **2. Dosya Yükleme (File Upload)**

- **Senaryo:** Bir "Dosya Seç" butonuna tıklayıp bilgisayarımızdan bir resim veya belge seçip siteye yüklemek istiyoruz.
- **Yaklaşım:**
    - Eğer HTML elementi `<input type="file">` şeklinde ise, işimiz çok kolay. Bu tip elementler için bir dosya seçme penceresi açmaya gerek yoktur.
    - `findElement` ile bu `<input>` elementini buluruz.
    - Bu elemente `click()` yapmak yerine, doğrudan `sendKeys()` metodu ile yüklemek istediğimiz dosyanın **tam yolunu** göndeririz. Selenium gerisini halleder.
- **Örnek:**Java
    
    `WebElement dosyaSecInput = driver.findElement(By.id("upload-input"));
    String yuklenecekDosyaYolu = System.getProperty("user.home") + "/Desktop/profil_fotografim.jpg";
    dosyaSecInput.sendKeys(yuklenecekDosyaYolu); // Tıklama yok, sadece dosya yolunu gönder!`
    

---

### **3: Excel Dosyaları ile Çalışma (Apache POI)**

Test verilerini (test data) Excel'de tutmak çok yaygın bir pratiktir (Data-Driven Testing). Java, kendi başına Excel dosyalarını okuyamaz. Bunun için **Apache POI** adı verilen güçlü bir kütüphaneyi projemize (`pom.xml`'e dependency olarak) eklememiz gerekir.

- **Excel'in Temel Kavramları:**
    - **Workbook (Çalışma Kitabı):** Excel dosyasının kendisidir (`.xlsx`).
    - **Sheet (Sayfa):** Workbook içindeki her bir sayfadır (Sayfa1, Sayfa2 vb.).
    - **Row (Satır):** Sayfa içindeki yatay sıralardır.
    - **Cell (Hücre):** Bir satır ve sütunun kesiştiği tek bir veri kutusudur.
- **Excel Okuma Adımları (Read Excel):**
    1. **Dosya Yolunu Belirle:** `String dosyaYolu = "src/test/resources/veriler.xlsx";`
    2. **Dosyayı Java'ya Tanıt:** `FileInputStream fis = new FileInputStream(dosyaYolu);`
    3. **POI ile Workbook Oluştur:** `Workbook workbook = WorkbookFactory.create(fis);`
    4. **Sayfaya Ulaş:** `Sheet sheet = workbook.getSheet("MusteriBilgileri");` (veya index ile `getSheetAt(0)`).
    5. **Satıra Ulaş:** `Row row = sheet.getRow(3);` (index 0'dan başlar, yani 4. satır).
    6. **Hücreye Ulaş ve Oku:** `Cell cell = row.getCell(1);` (index 0'dan başlar, yani B sütunu).
    7. Hücredeki veriyi `String`'e çevir: `String veri = cell.toString();`
- **Pratik Metotlar ve Senaryolar:**
    - **Belirli Bir Hücredeki Veriyi Alma:** Yukarıdaki 7 adımı takip ederek `B4` hücresindeki veriyi okumak.
    - **Kullanılan Satır Sayısını Bulma (`getPhysicalNumberOfRows()`):** Sayfada içinde veri olan toplam satır sayısını verir. Bir döngü kurup tüm satırları gezmek için kullanılır.
    - **Son Satırın Numarasını Bulma (`getLastRowNum()`):** Son satırın index'ini verir. Bu da döngüler için kullanışlıdır.
    - **Belirli Sütundaki Tüm Verileri Yazdırma:** Bir `for` döngüsü ile satır sayısınca dönülür ve her seferinde istenen sütunun (`cell`) index'i sabit tutularak veriler okunur.
    - **Tüm Tabloyu Yazdırma:** İç içe iki `for` döngüsü kullanılır. Dıştaki döngü satırları (`row`), içteki döngü ise o satırdaki hücreleri (`cell`) gezer.
    - **Excel'e Veri Yazma (Write Excel):**
        - Okuma adımlarına benzer şekilde hücreye ulaşılır.
        - Yeni bir hücre oluşturmak için: `row.createCell(5);`
        - Hücreye değer atamak için: `cell.setCellValue("YENİ VERİ");`
        - **ÇOK ÖNEMLİ:** Yazma işlemi bittikten sonra değişiklikleri kaydetmek için `FileOutputStream` kullanılır:
            - `FileOutputStream fos = new FileOutputStream(dosyaYolu);`
            - `workbook.write(fos);`
            - Dosyaları kapatmayı unutma: `fis.close(); fos.close(); workbook.close();`
    - **Excel'den Veri Silme:** Hücreye ulaştıktan sonra `row.removeCell(cell);` metodu kullanılır ve değişiklikler `FileOutputStream` ile kaydedilir.

---