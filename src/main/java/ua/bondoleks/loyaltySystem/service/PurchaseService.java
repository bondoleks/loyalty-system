package ua.bondoleks.loyaltySystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.bondoleks.loyaltySystem.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    Page<Purchase> getAllPurchases(Pageable pageable);
    Purchase getPurchaseById(Long id);
    void createPurchase(Purchase purchase);
    void updatePurchase(Long id, Purchase purchaseDetails);
    void deletePurchase(Long id);
}
