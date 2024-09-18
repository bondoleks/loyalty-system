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
    @JoinColumn(name = "user_id", nullable = false)
    private LUser user;

    @ElementCollection
    @CollectionTable(name = "purchase_items", joinColumns = @JoinColumn(name = "purchase_id"))
    private List<Item> items;

    private double totalAmount;

    private double bonusAmount;

    private boolean returned = false;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
