package ua.bondoleks.loyaltySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondoleks.loyaltySystem.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
