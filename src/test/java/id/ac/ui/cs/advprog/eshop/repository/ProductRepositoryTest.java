package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }


    // Initiate product
    Product initiateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        return product;
    }

    // Test for create and find product
    @Test
    void testCreateAndFind() {
        Product product = initiateProduct();

        Iterator <Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindProductById() {
        Product product = initiateProduct();
        Product savedProduct = productRepository.findProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product, savedProduct);

        Product unknownProduct = productRepository.findProductById("fb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(unknownProduct);
    }

    @Test
    void testFindProductByUnknownId() {
        Product savedProduct = productRepository.findProductById("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertNull(savedProduct);
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = initiateProduct();

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator <Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEmptyProductName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertEquals("Nama produk tidak boleh kosong", product.getProductName());
    }

    @Test
    void testNegativeProductQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(-100);
        productRepository.create(product);

        assertEquals(0, product.getProductQuantity());
    }

    // Test for edit product
    @Test
    void testEdit() {
        Product product = initiateProduct();
        product.setProductName("Sampo Cap Usep");
        product.setProductQuantity(50);
        Product savedProduct = productRepository.update(product);

        assertEquals(savedProduct, product);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        Product savedProduct2 = productRepository.update(product2);

        assertNull(savedProduct2);
    }

    @Test
    void testEditProductNameToEmpty() {
        Product product = initiateProduct();
        product.setProductName("");
        Product savedProduct = productRepository.update(product);
        assertEquals("Nama produk tidak boleh kosong", savedProduct.getProductName());
    }

    @Test
    void testEditQuantityToNegative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        product.setProductQuantity(-50);
        Product savedProduct = productRepository.update(product);

        assertEquals(0, savedProduct.getProductQuantity());
    }

    // Test for delete product
    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        productRepository.delete(product);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteUnknownProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.delete(product);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
}