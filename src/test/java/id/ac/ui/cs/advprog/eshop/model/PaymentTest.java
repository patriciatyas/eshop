package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("testKey", "testValue");
    }

    @Test
    void testCreatePayment() {
        payment = new Payment();
        payment.setId("payment-123");
        payment.setMethod("VOUCHER");
        payment.setStatus("SUCCESS");
        payment.setPaymentData(paymentData);
        payment.setOrder(new Order());

        assertEquals("payment-123", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
        assertNotNull(payment.getOrder());
    }

    @Test
    void testPaymentDataOperations() {
        payment = new Payment();
        payment.setPaymentData(paymentData);

        assertEquals("testValue", payment.getPaymentData().get("testKey"));

        // Test adding new data
        payment.getPaymentData().put("newKey", "newValue");
        assertEquals("newValue", payment.getPaymentData().get("newKey"));
    }
}