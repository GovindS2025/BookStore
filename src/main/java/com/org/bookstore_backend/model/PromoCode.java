package com.org.bookstore_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing promotional codes that can be applied to orders.
 */
@Entity
@Table(name = "promo_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromoCode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String code = "DEFAULT";
    
    @Column(nullable = false)
    private String description = "Promotional discount";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType = DiscountType.FIXED_AMOUNT;
    
    @Column(nullable = false)
    private BigDecimal discountValue = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private BigDecimal minimumOrderAmount = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private Integer maxUses = 1000;
    
    @Column(nullable = false)
    private Integer currentUses = 0;
    
    @Column(nullable = false)
    private LocalDateTime validFrom = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime validUntil = LocalDateTime.now().plusYears(1);
    
    @Column(nullable = false)
    private boolean active = true;
    
    /**
     * Checks if the promo code is currently valid.
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return active && 
               now.isAfter(validFrom) && 
               now.isBefore(validUntil) && 
               currentUses < maxUses;
    }
    
    /**
     * Calculates the discount amount for a given order total.
     */
    public BigDecimal calculateDiscount(BigDecimal orderTotal) {
        if (orderTotal.compareTo(minimumOrderAmount) < 0) {
            return BigDecimal.ZERO;
        }
        
        switch (discountType) {
            case PERCENTAGE:
                return orderTotal.multiply(discountValue).divide(new BigDecimal("100"));
            case FIXED_AMOUNT:
                return discountValue.compareTo(orderTotal) > 0 ? orderTotal : discountValue;
            default:
                return BigDecimal.ZERO;
        }
    }
    
    /**
     * Increments the usage count of this promo code.
     */
    public void incrementUsage() {
        this.currentUses++;
    }
    
    /**
     * Enum for different types of discounts.
     */
    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT
    }
}
