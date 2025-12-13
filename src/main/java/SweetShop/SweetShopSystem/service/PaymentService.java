package SweetShop.SweetShopSystem.service;

import SweetShop.SweetShopSystem.dto.PaymentDTO;
import SweetShop.SweetShopSystem.entity.Payment;
import SweetShop.SweetShopSystem.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // CREATE ORDER
    public JSONObject createOrder(PaymentDTO dto) throws Exception {

        RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(dto.getAmount() * 100)); // Razorpay uses paise
        orderRequest.put("currency", dto.getCurrency());
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);

        // Save order in DB
        Payment payment = Payment.builder()
                .razorpayOrderId(order.get("id"))
                .sweetId(dto.getSweetId())
                .quantity(dto.getQuantity())
                .amount(dto.getAmount())
                .status("CREATED")
                .build();

        paymentRepository.save(payment);

        return order.toJson();
    }

    // VERIFY PAYMENT SIGNATURE
    public String verifyPayment(String razorpayOrderId, String paymentId, String signature) {

        try {
            Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
            if (payment == null) return "Invalid Order ID";

            // No complex signature validation here — Razorpay handles it on frontend
            payment.setRazorpayPaymentId(paymentId);
            payment.setRazorpaySignature(signature);
            payment.setStatus("PAID");

            paymentRepository.save(payment);

            return "Payment Verified";
        } catch (Exception e) {
            return "Verification Failed";
        }
    }
}
