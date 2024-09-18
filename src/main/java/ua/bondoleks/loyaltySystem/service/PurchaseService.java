package ua.bondoleks.loyaltySystem.service;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.Purchase;
import ua.bondoleks.loyaltySystem.exception.PurchaseNotFoundException;
import ua.bondoleks.loyaltySystem.repository.PurchaseRepository;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with ID " + id + " not found"));
    }

    public void createPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void updatePurchase(Long id, Purchase purchaseDetails) {
        Purchase purchase = getPurchaseById(id);
        purchase.setItems(purchaseDetails.getItems());
        purchase.setTotalAmount(purchaseDetails.getTotalAmount());
        purchaseRepository.save(purchase);
    }

    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new PurchaseNotFoundException("Purchase with ID " + id + " not found");
        }
        purchaseRepository.deleteById(id);
    }
}