package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        // Input validation
        if ("".equals(product.getProductName())) {
            product.setProductName("Nama produk tidak boleh kosong");
        }
        if (product.getProductQuantity() < 0) {
            product.setProductQuantity(0);
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findProductById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product update(Product product) {
        // Input validation
        if ("".equals(product.getProductName())) {
            product.setProductName("Nama produk tidak boleh kosong");
        }
        if (product.getProductQuantity() < 0) {
            product.setProductQuantity(0);
        }

        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(product.getProductId())) {
                productData.set(i, product);
                return product;
            }
        }
        return null;
    }

    public void delete (Product product) {
        productData.remove(product);
    }
}