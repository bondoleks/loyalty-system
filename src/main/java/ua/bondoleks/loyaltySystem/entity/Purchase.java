package ua.bondoleks.loyaltySystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.bondoleks.loyaltySystem.entity.enums.StoreType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_phone_number", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private List<Product> products;

    private double totalAmount;

    private double bonusAmount;

    private boolean returned = false;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
