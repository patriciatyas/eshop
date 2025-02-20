Patricia Herningtyas - 2306152241

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
### Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
Sudah, karena saya menerapkan Automated Testing dan Code Quality Analysis. Setiap push dan pull request men-trigger test suite dan static code analysis. Hal ini dilakukan agar perubahan baru tidak menyebabkan regresi atau masalah kualitas kode. Ini juga sejalan dengan prinsip CI yang mengutamakan validasi kode sebelum digabungkan. Saya menerapkan CD disaat mendeploy aplikasi ke PaaS setelah semua pengujian berhasil. Ini berarti setiap perubahan yang lolos tahap pengujian akan langsung diterapkan ke _production environment_ tanpa interverensi manual.



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
