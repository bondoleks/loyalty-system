package ua.bondoleks.loyaltySystem.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.LSUser;
import ua.bondoleks.loyaltySystem.entity.Purchase;
import ua.bondoleks.loyaltySystem.exception.PurchaseNotFoundException;
import ua.bondoleks.loyaltySystem.repository.PurchaseRepository;
import ua.bondoleks.loyaltySystem.repository.UserRepository;
import ua.bondoleks.loyaltySystem.service.PurchaseService;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Purchase> getAllPurchases(Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with ID " + id + " not found"));
    }

    @Override
    public void createPurchase(Purchase purchase) {
        LSUser user = purchase.getUser();
        LSUser existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);

        if (existingUser == null) {
            user.setBalance((int) (purchase.getTotalAmount() * 0.05)); // 5% бонусів
            userRepository.save(user);
        } else {
            existingUser.setBalance(existingUser.getBalance() + (int) (purchase.getTotalAmount() * 0.05));
            purchase.setUser(existingUser);
            userRepository.save(existingUser);
        }

        if (purchase.isReturned()) {
            user.setBalance(user.getBalance() - (int) (purchase.getTotalAmount() * 0.05));
        }

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