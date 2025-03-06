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

class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Product> products = new ArrayList<>();
        products.add(new Product("1", "Product 1", 100));

        order = new Order("order-123", products, System.currentTimeMillis(), "user");

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABCD");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAddPaymentVoucher() {
        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(payment);
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("payment-123", "VOUCHER", "PENDING", paymentData, order);
        when(paymentRepository.findById("payment-123")).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", updatedPayment.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("payment-123", "VOUCHER", "PENDING", paymentData, order);
        when(paymentRepository.findById("payment-123")).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", updatedPayment.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("payment-123", "VOUCHER", "SUCCESS", paymentData);
        when(paymentRepository.findById("payment-123")).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment("payment-123");

        assertNotNull(foundPayment);
        assertEquals("payment-123", foundPayment.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("payment-123", "VOUCHER", "SUCCESS", paymentData));
        payments.add(new Payment("payment-456", "COD", "PENDING", paymentData));

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());
    }
}