Patricia Herningtyas - 2306152241

[![Continuous Integration (CI)](https://github.com/patriciatyas/eshop/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/patriciatyas/eshop/actions/workflows/ci.yml)
[![pmd](https://github.com/patriciatyas/eshop/actions/workflows/pmd.yml/badge.svg)](https://github.com/patriciatyas/eshop/actions/workflows/pmd.yml)
[![Scorecard supply-chain security](https://github.com/patriciatyas/eshop/actions/workflows/scorecard.yml/badge.svg)](https://github.com/patriciatyas/eshop/actions/workflows/scorecard.yml)


🔗 [Link ADV Shop](https://gothic-fionna-patriciatyas-af6852fc.koyeb.app/) 🔗

# Module 3 - Maintainability & OO Principles
### SOLID principles yang diterapkan:
1. Single Responsibility Principle (SRP)
   - Memisahkan `CarController` dari `ProductController` dan menjadikannya class sendiri
   - Memisahkan ID _generation_ dari CarRepository dan menjadikannya class sendiri
   Dengan demikian, setiap class sekarang memiliki tepat satu _responsibility_
2. Liskov Substitution Principle (LSP)
   - Menghapus extends dari CarController dan membuat CarController menjadi class sendiri (di file berbeda). Hal ini saya lakukan karena CarController memiliki perilaku yang berbeda dengan superclassnya.
3. Interface Segregation Principle (ISP)
   - ISP telah diterapkan pada CarService karena interface ini fokus pada satu hal, yaitu CRUD untuk Car.
4. Dependency Inversion Principle (DIP)
   - Mengganti tipe data dari variabel carService pada CarController yang awalnya CarServiceImpl menjadi CarService. Hal ini saya lakukan karena CarController seharusnya bergantung dengan interface CarService.

### Keuntungan dari mengimplementasikan SOLID principle
Dengan menerapkan prinsip SOLID. Kode dapat lebih mudah di-_maintain_. Contoh: Jika cara pembuatan ID diubah, hanya IdGeneratorService yang perlu dimodifikasi tanpa mengubah repository. Manfaat dari hal ini adalah perubahan lokal mengurangi risiko bug di bagian lain proyek.
Proyek yang menerapkan prinsip SOLID memiliki fondasi yang kuat, dapat menangani pertumbuhan dan perubahan di masa depan dengan lebih mudah, serta tetap mudah diuji dan dipelihara.

### Kekurangan dari tidak mengimplementasikan SOLID principle
Tanpa menerapkan prinsip SOLID, kode bisa menjadi sulit diperbaiki, tidak fleksibel, dan mudah mengalami error saat ada perubahan. Jika satu bagian kode diubah, bagian lain yang tidak berhubungan bisa ikut bermasalah karena semuanya terlalu saling bergantung. Selain itu, _testing_ juga menjadi lebih rumit karena sulit memisahkan bagian kode untuk di-_test_ secara mandiri.
Contoh: Jika SRP tidak diterapkan, anggota tim lain dapat kesulitan menemukan metode-metode dalam CarController karena masih tergabung dalam ProductController, sehingga membingungkan dan memperumit pemahaman kode.

<details>
<summary>Module 2 - CI/CD & Dev Ops</summary>

# Module 2 - CI/CD & Dev Ops

### List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
1. Saya menghapus modifier yang tidak perlu dalam interface ProductService. Dalam Java, semua metode di dalam sebuah interface secara implisit bersifat public dan abstract, sehingga menambahkan modifier public secara eksplisit menjadi redundan. Dengan menghapusnya, kode menjadi lebih bersih dan mengikuti _best practice_ dalam java, meningkatkan _readability_ dan _code maintenance_.
    Permasalahan:  
    ``` 
    public interface ProductService {
        public Product create(Product product);
        public List<Product> findAll();
        public Product findProductById (String productId);
        public Product update(Product product);
        public void delete (Product product);
    }
    ```
   Perbaikan:
   ```
    public interface ProductService {
        Product create(Product product);
        List<Product> findAll();
        Product findProductById (String productId);
        Product update(Product product);
        void delete (Product product);
   }
   ```
2. Menghapus import yang tidak terpakai di HomePageController
    ```
    import org.springframework.web.bind.annotation.*;

    ```
   menjadi
    ```
   import org.springframework.web.bind.annotation.GetMapping;

   ```
3. Mengubah string comparisons dari
    ```
   if (product.getProductName().equals(""))
   ```
   menjadi
    ```
   if ("".equals(product.getProductName()))
   ```
   untuk mencegah NullPointerException jika getProductName() adalah null (akan me-return false jika null)
4. Menambahkan {} untuk if conditions agar kode memiliki _readability_ dan _maintainability_ yang lebih baik. 

    Sebelum: 
   ```
   if ("".equals(product.getProductName())) product.setProductName("Nama produk tidak boleh kosong");
    if (product.getProductQuantity() < 0) product.setProductQuantity(0);
   ```
    Setelah:
    ```
    if ("".equals(product.getProductName())) {
       product.setProductName("Nama produk tidak boleh kosong");
    }
    if (product.getProductQuantity() < 0) {
       product.setProductQuantity(0);
    }
    ```
5. Mengubah "redirect:/product/list" menjadi sebuah private static final constant di ProductController agar tidak melanggar DRY (Don't Repeat Yourself) principle.

    Sebelum:
    ```
    public String editProductPost(@ModelAttribute Product product){
        service.update(product);
        return "redirect:/product/list";
    }
    ```
    Setelah:
    ```
    private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";
    .
    .
    public String editProductPost(@ModelAttribute Product product){
        service.update(product);
        return REDIRECT_PRODUCT_LIST;
    }
    ```
### Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
Sudah, karena saya menerapkan Automated Testing dan Code Quality Analysis. Setiap push dan pull request men-trigger test suite dan static code analysis. Hal ini dilakukan agar perubahan baru tidak menyebabkan regresi atau masalah kualitas kode. Ini juga sejalan dengan prinsip CI yang mengutamakan validasi kode sebelum digabungkan. Saya menerapkan CD disaat mendeploy aplikasi ke PaaS setelah semua pengujian berhasil. Ini berarti setiap perubahan yang lolos tahap pengujian akan langsung diterapkan ke _production environment_ tanpa interverensi manual. Dengan demikian, proses pengembangan menjadi lebih efisien dan memastikan bahwa fitur baru atau perbaikan dapat segera digunakan oleh pengguna.
</details>


<details>
<summary>Module 1 - Coding Standards</summary>

# Module 1 - Coding Standards

## Reflection 1
<h3>Penerapan Prinsip Clean Code<h3>

**1. Meaningful Names**

Untuk tutorial ini, saya menuliskan nama variabel, _function_, _class_, dan _argument_ sejelas mungkin agar tidak perlu menjelaskan lagi menggunakan _comment_.
Contoh:
```
void testEmptyProductName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertEquals("Nama produk tidak boleh kosong", product.getProductName());
    }
```
**2. Functions**

Untuk _functions_, saya membuat _functions_ yang hanya bisa melakukan satu hal, kecil, dan tidak memiliki efek samping.

**3. Comments**

Saya tidak menambahkan _comment_ untuk kode yang sudah jelas seperti pada poin pertama. Saya juga menambahkan _comment_ se-singkat, padat, dan jelas mungkin.

**4. Objects and Data Structures**

Saya menempatkan UUID Product di dalam constructor Product itu sendiri, sesuai dengan prinsip OOP, daripada meletakkannya di ProductService.java atau ProductRepository.java.

**5. Error Handling**

Saya membuat _handling_ untuk _invalid input_ dengan membagi-baginya menjadi beberapa functions agar lebih rapih dan mudah dibaca.

## Cara saya mengembangkan code
Saya membaca dokumentasi di internet dan stackoverflow jika menemukan error.

## Reflection 2

1. Saya jadi mudah menemukan bug dalam code saya. Saya tidak perlu memasukan input secara manual karena adanya unit-test.
Berapa banyak test yang diperlukan dalam sebuah 'Class'?**
Tidak ada batasan untuk membuat test. Menurut saya, semakin banyak test maka semakin baik. Namun, kita harus tetap memperhatikan fungsionalitas dari test tersebut.
Jika code coverage saya 100%, bukan berarti kode saya tidak memiliki _bugs_ atau _error_ karena code coverage hanya mengukur seberapa banyak baris atau cabang kode yang dieksekusi oleh _testing_, tetapi tidak menjamin bahwa semua kemungkinan kasus atau skenario telah diuji dengan benar.
Misalnya, bisa jadi _testing_ tidak mencakup semua kasus edge, tidak menguji interaksi antar komponen, atau ada kesalahan logika.
Jadi, meskipun code coverage tinggi itu baik, kualitas pengujian dan cakupan skenario _testing_ jauh lebih penting untuk memastikan kode benar-benar bebas dari bug.

2. Menurut pendapat saya, hal tersebut mengurangi kualitas dari _clean code_. Menggunakan suatu prosedur dan variabel yang sama membuat kode menjadi tidak efisien. Oleh karena itu, kita bisa membuatnya ke dalam satu 'Class'. Setelah itu, dipisah menjadi _function_ yang berbeda-beda untuk setiap _test_nya.
</details>
