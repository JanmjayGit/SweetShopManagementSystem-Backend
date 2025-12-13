package SweetShop.SweetShopSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    private Long sweetId;
    private int quantity;

    private double amount;

    private String status; // CREATED, PAID, FAILED

    @Column(updatable = false)
    private Long createdAt = System.currentTimeMillis();
}
