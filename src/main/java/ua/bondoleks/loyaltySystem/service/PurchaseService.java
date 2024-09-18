package ua.bondoleks.loyaltySystem.service;

import ua.bondoleks.loyaltySystem.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> getAllPurchases();
    Purchase getPurchaseById(Long id);
    void createPurchase(Purchase purchase);
    void updatePurchase(Long id, Purchase purchaseDetails);
    void deletePurchase(Long id);
}
