package SweetShop.SweetShopSystem.repository;

import SweetShop.SweetShopSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByRazorpayOrderId(String orderId);
}
