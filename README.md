<h1>Module 1 - Coding Standards</h1>

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

