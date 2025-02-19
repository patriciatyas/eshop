package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        Product product2 = new Product();
        Iterator<Product> iterator = Arrays.asList(product1, product2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> productList = productService.findAll();
        assertEquals(2, productList.size());
        assertTrue(productList.contains(product1));
        assertTrue(productList.contains(product2));
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        when(productRepository.findProductById("1")).thenReturn(product);
        Product foundProduct = productService.findProductById("1");
        assertEquals(product, foundProduct);
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        when(productRepository.update(product)).thenReturn(product);
        Product updatedProduct = productService.update(product);
        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        Product product = new Product();
        doNothing().when(productRepository).delete(product);
        productService.delete(product);
        verify(productRepository, times(1)).delete(product);
    }
}
