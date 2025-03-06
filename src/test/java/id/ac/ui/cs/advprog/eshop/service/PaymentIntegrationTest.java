package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        // Create a product for testing
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        // Create an order
        order = new Order("test-order-" + System.currentTimeMillis(), products, System.currentTimeMillis(), "testUser");
        orderService.createOrder(order);
    }

    @Test
    void testSuccessfulVoucherPaymentUpdatesOrderStatus() {
        // Create payment data with valid voucher
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678XYZ");

        // Process payment
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        // Verify payment status
        assertEquals("SUCCESS", payment.getStatus());

        // Verify order status was updated
        Order updatedOrder = orderService.findById(order.getId());
        assertEquals("SUCCESS", updatedOrder.getStatus());
    }

    @Test
    void testRejectedVoucherPaymentUpdatesOrderStatus() {
        // Create payment data with invalid voucher
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID123456");

        // Process payment
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        // Verify payment status
        assertEquals("REJECTED", payment.getStatus());

        // Verify order status was updated
        Order updatedOrder = orderService.findById(order.getId());
        assertEquals("FAILED", updatedOrder.getStatus());
    }

    @Test
    void testSuccessfulCashOnDeliveryPaymentUpdatesOrderStatus() {
        // Create payment data
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Test Address");
        paymentData.put("deliveryFee", "10000");

        // Process payment
        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        // Verify payment status
        assertEquals("SUCCESS", payment.getStatus());

        // Verify order status was updated
        Order updatedOrder = orderService.findById(order.getId());
        assertEquals("SUCCESS", updatedOrder.getStatus());
    }

    @Test
    void testRejectedCashOnDeliveryPaymentUpdatesOrderStatus() {
        // Create payment data with missing information
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Test Address");
        // Missing delivery fee

        // Process payment
        Payment payment = paymentService.addPayment(order, "COD", paymentData);

        // Verify payment status
        assertEquals("REJECTED", payment.getStatus());

        // Verify order status was updated
        Order updatedOrder = orderService.findById(order.getId());
        assertEquals("FAILED", updatedOrder.getStatus());
    }
}