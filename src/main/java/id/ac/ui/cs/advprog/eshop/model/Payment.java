package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    // Additional constructor for convenience
    public Payment(String id, String method, String status, Order order) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.order = order;
        this.paymentData = new HashMap<>();
    }
}