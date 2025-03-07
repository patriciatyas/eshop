package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CashOnDeliveryPaymentTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create an order for testing
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-123", products, System.currentTimeMillis(), "user1");

        // Mock repository behavior
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testValidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100, Depok");
        paymentData.put("deliveryFee", "15000");

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryMissingAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "15000");

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "15000");

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryMissingDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100, Depok");

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryEmptyDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Margonda Raya No. 100, Depok");
        paymentData.put("deliveryFee", "");

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidCashOnDeliveryNullValues() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", null);

        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}