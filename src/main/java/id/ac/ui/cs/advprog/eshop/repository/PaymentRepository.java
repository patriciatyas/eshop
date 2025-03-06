package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int index = getIndex(payment.getId());

        if (index != -1) {
            paymentData.set(index, payment);
        } else {
            paymentData.add(payment);
        }

        return payment;
    }

    public Payment findById(String id) {
        return paymentData.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }

    private int getIndex(String id) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}