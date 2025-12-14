package SweetShop.SweetShopSystem.controller;

import SweetShop.SweetShopSystem.dto.PaymentDTO;
import SweetShop.SweetShopSystem.dto.PaymentVerifyDTO;
import SweetShop.SweetShopSystem.service.PaymentService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //  CREATE PAYMENT ORDER
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentDTO dto) {
        try {
            JSONObject order = paymentService.createOrder(dto);
            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //  VERIFY PAYMENT AFTER SUCCESS
    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerifyDTO dto) {
        String result = paymentService.verifyPayment(
                dto.getRazorpayOrderId(),
                dto.getRazorpayPaymentId(),
                dto.getRazorpaySignature()
        );
        return ResponseEntity.ok(result);
    }

}
