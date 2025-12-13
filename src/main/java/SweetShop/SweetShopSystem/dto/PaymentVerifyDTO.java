package SweetShop.SweetShopSystem.dto;

import lombok.Data;

@Data
public class PaymentVerifyDTO {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
