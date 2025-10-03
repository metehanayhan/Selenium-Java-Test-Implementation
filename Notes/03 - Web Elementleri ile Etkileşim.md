# Web Elementleri ile Etkileşim

Bu bölüm, temel `click()` ve `sendKeys()` komutlarının ötesine geçerek, modern web uygulamalarında sıkça karşılaşılan dinamik ve özel web elementlerini nasıl kontrol edeceğimizi ve doğrulayacağımızı kapsar.

---

### **1. CheckBox (Onay Kutusu) ve RadioButton (Seçim Düğmesi)**

Bu ikisi, formlarda en sık karşılaşılan elementlerdendir. Checkbox'lar birden fazla seçime izin verirken, aynı gruptaki radio button'lar sadece tek bir seçime izin verir. Otomasyondaki mantıkları çok benzerdir.

- **Temel Etkileşimler:**
    - **Seçme:** Her ikisi de standart `click()` metodu ile seçilir veya seçimi kaldırılır (sadece checkbox için).
        - `WebElement checkbox = driver.findElement(By.id("terms"));`
        - `checkbox.click();`
    - **Doğrulama (Verification):** Asıl önemli olan, bu elementlerin durumunu kontrol etmektir. Bunun için 3 önemli metot kullanılır:
        - `isSelected()`: Elementin seçili olup olmadığını kontrol eder. `true` (seçili) veya `false` (seçili değil) döner. Assertion'lar için hayati öneme sahiptir.
        - `isDisplayed()`: Elementin sayfada görünür olup olmadığını kontrol eder.
        - `isEnabled()`: Elemente tıklanıp tıklanamayacağını (aktif olup olmadığını) kontrol eder.
- **Örnek Senaryo (Checkbox):**
    1. Bir checkbox elementi bulunur.
    2. `assertFalse(checkbox.isSelected());` // Başlangıçta seçili olmadığını doğrula.
    3. `checkbox.click();` // Checkbox'ı seç.
    4. `assertTrue(checkbox.isSelected());` // Şimdi seçili olduğunu doğrula.
- **Örnek Senaryo (Radio Button):**
    1. "Erkek" ve "Kadın" radio button'ları bulunur.
    2. `erkekRadioButton.click();` // "Erkek" seçilir.
    3. `assertTrue(erkekRadioButton.isSelected());` // "Erkek"in seçili olduğunu doğrula.
    4. `assertFalse(kadinRadioButton.isSelected());` // "Kadın"ın seçili olmadığını doğrula.

---

### **2. DropDown Menu (Açılır Menü)**

HTML'deki `<select>` etiketi ile oluşturulan menülerdir. Selenium, bu menülerle çalışmayı kolaylaştırmak için özel bir `Select` sınıfı sunar.

- **`Select` Sınıfının Kullanımı:**
    1. Önce dropdown elementinin kendisini (`<select>` etiketini) bulmamız gerekir.
        - `WebElement dropdownElement = driver.findElement(By.id("dropdown"));`
    2. Bu `WebElement`'i kullanarak bir `Select` objesi oluştururuz.
        - `Select select = new Select(dropdownElement);`
    3. Artık `select` objesinin metotlarıyla istediğimiz seçeneği kolayca seçebiliriz:
        - `select.selectByVisibleText("Seçenek 2");` // Görünen metne göre seçer (En çok tercih edilen).
        - `select.selectByValue("2");` // `<option>` etiketinin `value` attribute'una göre seçer.
        - `select.selectByIndex(2);` // Sırasına göre seçer (0'dan başlar). (En az güvenilir olan).
- **Seçili Değeri Kontrol Etme:**
    - `select.getFirstSelectedOption()`: O an seçili olan `<option>` elementini bir `WebElement` olarak döndürür. Bu elementin metnini `.getText()` ile alıp doğrulama yapabiliriz.
    - **Örnek:** `String seciliMetin = select.getFirstSelectedOption().getText();`

---

### **3. JS Alerts (JavaScript Uyarıları)**

JavaScript ile oluşturulan ve tarayıcının normal akışını durduran küçük pop-up pencerelerdir. Bunlar HTML'in bir parçası olmadıkları için `findElement` ile bulunamazlar.

- **`Alert` Arayüzünün Kullanımı:**
    1. Selenium'un kotrolünü (focus'unu) bu uyarıya geçirmemiz gerekir.
        - `driver.switchTo().alert();`
    2. Bu komut bize bir `Alert` objesi döndürür. Bu obje ile uyarıyı yönetebiliriz.
        - `Alert alert = driver.switchTo().alert();`
    3. Alert üzerindeki işlemler:
        - `alert.accept();` // "Tamam", "OK" gibi pozitif butona tıklar.
        - `alert.dismiss();` // "İptal", "Cancel" gibi negatif butona veya 'X'e tıklar.
        - `alert.getText();` // Uyarının içindeki metni String olarak alır. Doğrulama için kullanılır.
        - `alert.sendKeys("Metin");` // Sadece `prompt` tipindeki (kullanıcıdan girdi isteyen) uyarılara metin gönderir.

---

### **4. IFrame (Sayfa İçindeki Sayfa)**

Bir `<iframe>`, bir HTML sayfası içine başka bir HTML sayfası gömmemizi sağlar. Selenium için bu, iki ayrı dünya demektir. `driver` varsayılan olarak ana sayfayı görür; iframe içindeki elementleri göremez.

- **Sorun:** Bir element `iframe` içindeyse, doğru locator'ı yazsanız bile `NoSuchElementException` hatası alırsınız.
- **Çözüm:** `driver`'ın kontrolünü iframe'in içine "geçirmemiz" (`switch`) gerekir.
- **IFrame'e Geçiş Yapma (`switchTo().frame()`):**
    1. **ID veya Name ile (En Güvenilir):**
        - `driver.switchTo().frame("iframe_id");`
    2. **Index ile (Kırılgan):** Sayfadaki kaçıncı iframe olduğuna göre (0'dan başlar).
        - `driver.switchTo().frame(0);`
    3. **WebElement ile:** Önce `<iframe>` etiketinin kendisini bulup sonra o elemente geçiş yapılır.
        - `WebElement iframeElement = driver.findElement(By.tagName("iframe"));`
        - `driver.switchTo().frame(iframeElement);`
- **IFrame'den Geri Çıkma:**
    - İşiniz bittiğinde mutlaka ana sayfaya geri dönmelisiniz.
    - `driver.switchTo().defaultContent();` // En üste, ana sayfaya geri döner.
    - `driver.switchTo().parentFrame();` // İç içe iframe'ler varsa, bir üstteki iframe'e döner.

---

### **5. WindowHandle (Pencere/Sekme Yönetimi)**

Bir linke tıkladığımızda yeni bir sekme veya pencere açılabilir. Bu durumda `driver`'ın kontrolü hala eski sekmede kalır. Yeni sekmeye geçiş yapmak için "Window Handle"ları kullanırız.

- **Window Handle Nedir?** WebDriver tarafından açılan her pencere/sekme için oluşturulan benzersiz bir kimlik (ID) kodudur.
- **Yeni Sekmeye Geçiş Süreci:**
    1. Yeni sekme açılmadan **önce**, mevcut sekmenin handle değerini alın.
        - `String anaSayfaHandle = driver.getWindowHandle();`
    2. Yeni sekmeyi açacak olan linke veya butona tıklayın.
    3. Açık olan **tüm** pencerelerin handle değerlerini bir `Set` içerisinde alın.
        - `Set<String> tumHandlelar = driver.getWindowHandles();`
    4. Bu `Set` içinde gezerek, ana sayfanın handle değerine eşit **olmayan** handle'ı bulun. Bu, yeni sekmenin handle'ıdır.Java
        
        ```jsx
        for (String handle : tumHandlelar) {
            if (!handle.equals(anaSayfaHandle)) {
                driver.switchTo().window(handle); // Yeni sekmeye geçiş yap!
                break;
            }
        }
        ```
        
    5. Yeni sekmede işlemlerinizi yapın (örn: `driver.getTitle()` ile başlığı doğrulayın).
    6. İşiniz bitince isterseniz o sekmeyi `driver.close()` ile kapatıp, `driver.switchTo().window(anaSayfaHandle);` komutuyla ana sayfaya geri dönebilirsiniz.

---