package ua.bondoleks.loyaltySystem.service;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.Purchase;
import ua.bondoleks.loyaltySystem.entity.LUser;
import ua.bondoleks.loyaltySystem.repository.PurchaseRepository;
import ua.bondoleks.loyaltySystem.repository.UserRepository;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public void createPurchase(Purchase purchase) {
        String phoneNumber = purchase.getUser().getPhoneNumber();
        LUser user = userRepository.findByPhoneNumber(phoneNumber).orElseGet(() -> {
            LUser newUser = purchase.getUser();
            return userRepository.save(newUser);
        });
        purchase.setUser(user);
        purchaseRepository.save(purchase);
    }

    public void updatePurchase(Long id, Purchase purchaseDetails) {
        Purchase purchase = getPurchaseById(id);
        purchase.setItems(purchaseDetails.getItems());
        purchase.setTotalAmount(purchaseDetails.getTotalAmount());
        purchase.setBonusAmount(purchaseDetails.getBonusAmount());
        purchase.setReturned(purchaseDetails.isReturned());
        purchase.setStoreType(purchaseDetails.getStoreType());
        purchaseRepository.save(purchase);
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}