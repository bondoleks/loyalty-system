package ua.bondoleks.loyaltySystem.entity;

import jakarta.persistence.Embeddable;

@Embeddable
class Item {
    private String article;
    private String photoUrl;
    private double price;
}
