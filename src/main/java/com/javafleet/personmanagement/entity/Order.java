package com.javafleet.personmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order Entity mit ManyToOne Relationship zu Person
 * 
 * @author Elyndra Valen
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String orderNumber;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    /**
     * ManyToOne zur Person
     * LAZY = Person wird nicht automatisch geladen
     * @ToString.Exclude verhindert StackOverflowError
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;
    
    /**
     * Lifecycle Callback: Wird automatisch VOR dem ersten Speichern aufgerufen
     * Setzt automatisch Datum und Status wenn nicht vorhanden
     */
    @PrePersist
    protected void onCreate() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }
}
