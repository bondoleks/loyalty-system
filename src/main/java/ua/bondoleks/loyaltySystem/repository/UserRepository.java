package ua.bondoleks.loyaltySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondoleks.loyaltySystem.entity.LSUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<LSUser, Long> {

    Optional<LSUser> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<LSUser> deleteByPhoneNumber(String phoneNumber);
}
