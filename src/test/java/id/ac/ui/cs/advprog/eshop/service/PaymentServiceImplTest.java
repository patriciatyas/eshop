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

import java.util.*;

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
    private Map<String, String> voucherPaymentData;
    private Map<String, String> codPaymentData;

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

        // Set up payment data
        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP12345678ABC");

        codPaymentData = new HashMap<>();
        codPaymentData.put("address", "Jakarta");
        codPaymentData.put("deliveryFee", "10000");

        // Mock repository behavior
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAddPaymentWithVoucher() {
        // Valid voucher code
        voucherPaymentData.put("voucherCode", "ESHOP12345678ABC");

        Payment payment = paymentService.addPayment(order, "VOUCHER", voucherPaymentData);

        assertNotNull(payment);
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(order, payment.getOrder());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        // Invalid voucher code (not starting with ESHOP)
        voucherPaymentData.put("voucherCode", "INVALID12345678");

        Payment payment = paymentService.addPayment(order, "VOUCHER", voucherPaymentData);

        assertNotNull(payment);
        assertEquals("REJECTED", payment.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithCOD() {
        // Valid COD data
        Payment payment = paymentService.addPayment(order, "COD", codPaymentData);

        assertNotNull(payment);
        assertEquals("COD", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithInvalidCOD() {
        // Missing delivery fee
        Map<String, String> invalidCodData = new HashMap<>();
        invalidCodData.put("address", "Jakarta");

        Payment payment = paymentService.addPayment(order, "COD", invalidCodData);

        assertNotNull(payment);
        assertEquals("REJECTED", payment.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("payment-123", "VOUCHER", "WAITING", voucherPaymentData, order);
        when(orderService.updateStatus(order.getId(), "SUCCESS")).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", updatedPayment.getStatus());
        verify(orderService).updateStatus(order.getId(), "SUCCESS");
        verify(paymentRepository).save(payment);
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("payment-123", "VOUCHER", "WAITING", voucherPaymentData, order);
        when(orderService.updateStatus(order.getId(), "FAILED")).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", updatedPayment.getStatus());
        verify(orderService).updateStatus(order.getId(), "FAILED");
        verify(paymentRepository).save(payment);
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("payment-123", "VOUCHER", "SUCCESS", voucherPaymentData, order);
        when(paymentRepository.findById("payment-123")).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment("payment-123");

        assertNotNull(foundPayment);
        assertEquals("payment-123", foundPayment.getId());
        verify(paymentRepository).findById("payment-123");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(
                new Payment("payment-1", "VOUCHER", "SUCCESS", voucherPaymentData, order),
                new Payment("payment-2", "COD", "SUCCESS", codPaymentData, order)
        );
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());
        verify(paymentRepository).findAll();
    }

    @Test
    void testAddPaymentWithUnknownMethod() {
        Map<String, String> unknownPaymentData = new HashMap<>();
        Payment payment = paymentService.addPayment(order, "UNKNOWN", unknownPaymentData);

        assertNotNull(payment);
        assertEquals("WAITING", payment.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testValidateVoucherCodeNull() {
        assertEquals("REJECTED", paymentService.addPayment(order, "VOUCHER", new HashMap<>()).getStatus());
    }

    @Test
    void testValidateVoucherCodeTooShort() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");
        assertEquals("REJECTED", paymentService.addPayment(order, "VOUCHER", paymentData).getStatus());
    }

    @Test
    void testValidateVoucherCodeTooLong() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123456789012");
        assertEquals("REJECTED", paymentService.addPayment(order, "VOUCHER", paymentData).getStatus());
    }

    @Test
    void testValidateVoucherCodeNotEnoughNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGH1234");
        assertEquals("REJECTED", paymentService.addPayment(order, "VOUCHER", paymentData).getStatus());
    }

    @Test
    void testValidateVoucherCodeTooManyNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234567890");
        assertEquals("REJECTED", paymentService.addPayment(order, "VOUCHER", paymentData).getStatus());
    }

    @Test
    void testValidateCashOnDeliveryAddressNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "10000");

        assertEquals("REJECTED", paymentService.addPayment(order, "COD", paymentData).getStatus());
    }

    @Test
    void testValidateCashOnDeliveryFeeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jakarta");

        assertEquals("REJECTED", paymentService.addPayment(order, "COD", paymentData).getStatus());
    }

    @Test
    void testValidateCashOnDeliveryBothNull() {
        Map<String, String> paymentData = new HashMap<>();

        assertEquals("REJECTED", paymentService.addPayment(order, "COD", paymentData).getStatus());
    }

    @Test
    void testValidateVoucherCodeWrongPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SHOP12345678ABC"); // Does not start with "ESHOP"

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}