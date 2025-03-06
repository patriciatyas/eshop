package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        String status = determinePaymentStatus(method, paymentData);

        Payment payment = new Payment(paymentId, method, status, paymentData, order);

        if (status.equals("SUCCESS") || status.equals("REJECTED")) {
            setStatus(payment, status);
            return payment;
        }

        return paymentRepository.save(payment);
    }


    private String determinePaymentStatus(String method, Map<String, String> paymentData) {
        switch (method) {
            case "VOUCHER":
                return validateVoucherCode(paymentData.get("voucherCode"));
            case "COD":
                return validateCashOnDelivery(paymentData);
            default:
                return "WAITING";
        }
    }

    private String validateVoucherCode(String voucherCode) {
        // Null check
        if (voucherCode == null) {
            return "REJECTED";
        }

        // Length validation
        if (voucherCode.length() != 16) {
            return "REJECTED";
        }

        // Prefix validation
        if (!voucherCode.startsWith("ESHOP")) {
            return "REJECTED";
        }

        // Count numerical characters
        long numCount = voucherCode.chars().filter(Character::isDigit).count();
        if (numCount != 8) {
            return "REJECTED";
        }

        return "SUCCESS";
    }

    private String validateCashOnDelivery(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
            return "REJECTED";
        }

        return "SUCCESS";
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        // Update order status based on payment status
        if (status.equals("SUCCESS")) {
            orderService.updateStatus(payment.getOrder().getId(), "SUCCESS");
        } else if (status.equals("REJECTED")) {
            orderService.updateStatus(payment.getOrder().getId(), "FAILED");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}