package ua.bondoleks.loyaltySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondoleks.loyaltySystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
