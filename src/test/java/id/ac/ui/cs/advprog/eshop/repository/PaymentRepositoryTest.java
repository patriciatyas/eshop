package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
        paymentData.put("testKey", "testValue");
        payment = new Payment("payment-123", "VOUCHER", "SUCCESS", paymentData);
    }

    @Test
    void testSavePayment() {
        Payment savedPayment = paymentRepository.save(payment);

        assertEquals(payment.getId(), savedPayment.getId());
        assertEquals(payment.getMethod(), savedPayment.getMethod());
        assertEquals(payment.getStatus(), savedPayment.getStatus());
        assertEquals(payment.getPaymentData(), savedPayment.getPaymentData());
    }

    @Test
    void testFindPaymentById() {
        paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById("payment-123");

        assertNotNull(foundPayment);
        assertEquals("payment-123", foundPayment.getId());
    }

    @Test
    void testFindPaymentByIdNotFound() {
        Payment foundPayment = paymentRepository.findById("non-existent-id");
        assertNull(foundPayment);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);

        Payment payment2 = new Payment("payment-456", "COD", "SUCCESS", paymentData);
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();

        assertEquals(2, payments.size());
        assertTrue(payments.contains(payment));
        assertTrue(payments.contains(payment2));
    }

    @Test
    void testUpdatePayment() {
        paymentRepository.save(payment);

        payment.setStatus("REJECTED");
        Payment updatedPayment = paymentRepository.save(payment);

        assertEquals("REJECTED", updatedPayment.getStatus());

        Payment foundPayment = paymentRepository.findById("payment-123");
        assertEquals("REJECTED", foundPayment.getStatus());
    }
}