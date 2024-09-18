package ua.bondoleks.loyaltySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondoleks.loyaltySystem.entity.LUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<LUser, Long> {

    Optional<LUser> findByPhoneNumber(String phoneNumber);

    Optional<LUser> deleteByPhoneNumber(String phoneNumber);
}
