package ua.bondoleks.loyaltySystem.service.impl;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.Purchase;
import ua.bondoleks.loyaltySystem.exception.PurchaseNotFoundException;
import ua.bondoleks.loyaltySystem.repository.PurchaseRepository;
import ua.bondoleks.loyaltySystem.service.PurchaseService;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with ID " + id + " not found"));
    }

    @Override
    public void createPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Override
    public void updatePurchase(Long id, Purchase purchaseDetails) {
        Purchase purchase = getPurchaseById(id);
        purchase.setItems(purchaseDetails.getItems());
        purchase.setTotalAmount(purchaseDetails.getTotalAmount());
        purchaseRepository.save(purchase);
    }

    @Override
    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new PurchaseNotFoundException("Purchase with ID " + id + " not found");
        }
        purchaseRepository.deleteById(id);
    }
}