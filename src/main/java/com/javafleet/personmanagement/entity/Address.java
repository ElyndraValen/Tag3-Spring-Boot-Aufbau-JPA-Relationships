package com.javafleet.personmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Address Entity mit ManyToOne Relationship zu Person
 * 
 * @author Elyndra Valen
 */
@Entity
@Table(name = "addresses")
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Straße darf nicht leer sein")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String street;
    
    @NotBlank(message = "Stadt darf nicht leer sein")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String city;
    
    @NotBlank(message = "PLZ darf nicht leer sein")
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String zipCode;
    
    @Size(max = 100)
    @Column(length = 50)
    private String country;
    
    /**
     * Typ der Adresse (HOME, WORK, BILLING, SHIPPING)
     * EnumType.STRING speichert den Namen als Text statt als Zahl
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AddressType type;
    
    /**
     * Bidirectional ManyToOne zu Person
     * LAZY = Person wird nicht automatisch geladen
     * @ToString.Exclude verhindert StackOverflowError bei toString()
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @JsonBackReference("person-addresses")
    private Person person;

    public Address() {
    }

    
    
    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    
    
    
}
