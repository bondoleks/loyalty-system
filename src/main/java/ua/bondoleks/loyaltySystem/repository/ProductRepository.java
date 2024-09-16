package ua.bondoleks.loyaltySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondoleks.loyaltySystem.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
