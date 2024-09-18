package ua.bondoleks.loyaltySystem.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bondoleks.loyaltySystem.entity.Purchase;
import ua.bondoleks.loyaltySystem.service.PurchaseService;

import java.util.List;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String getAllPurchases(Model model) {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        model.addAttribute("purchases", purchases);
        return "purchases";
    }

    @GetMapping("/new")
    public String createPurchaseForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        return "create_purchase";
    }

    @PostMapping
    public String createPurchase(@ModelAttribute Purchase purchase) {
        purchaseService.createPurchase(purchase);
        return "redirect:/purchases";
    }

    @GetMapping("/edit/{id}")
    public String editPurchaseForm(@PathVariable Long id, Model model) {
        Purchase purchase = purchaseService.getPurchaseById(id);
        model.addAttribute("purchase", purchase);
        return "edit_purchase";
    }

    @PostMapping("/update/{id}")
    public String updatePurchase(@PathVariable Long id, @ModelAttribute Purchase purchaseDetails) {
        purchaseService.updatePurchase(id, purchaseDetails);
        return "redirect:/purchases";
    }

    @GetMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return "redirect:/purchases";
    }
}