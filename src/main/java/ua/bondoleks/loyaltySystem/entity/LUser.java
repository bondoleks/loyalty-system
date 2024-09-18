package ua.bondoleks.loyaltySystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.bondoleks.loyaltySystem.entity.enums.Role;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private int balance = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();
}