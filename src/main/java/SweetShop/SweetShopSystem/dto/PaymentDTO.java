package SweetShop.SweetShopSystem.dto;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long sweetId;       // which sweet user is purchasing
    private int quantity;       // units purchased
    private double amount;      // total amount
    private String currency;    // INR
}
