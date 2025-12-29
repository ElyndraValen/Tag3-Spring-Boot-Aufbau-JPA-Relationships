package com.javafleet.personmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Person Entity mit zwei OneToMany Relationships (Addresses und Orders)
 * 
 * @author Elyndra Valen
 */
@Entity
@Table(name = "persons")
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Vorname darf nicht leer sein")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String firstname;
    
    @NotBlank(message = "Nachname darf nicht leer sein")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String lastname;
    
    @Email(message = "Email muss valid sein")
    @Column(unique = true)
    private String email;
    
    /**
     * OneToMany zu Adressen
     * mappedBy = "person" → Address ist der Owner (hat Foreign Key)
     * cascade = ALL → Alle Operationen werden zu Adressen kaskadiert
     * orphanRemoval = true → Adressen ohne Person werden automatisch gelöscht
     * fetch = LAZY → Adressen werden nicht automatisch geladen
     */
    @OneToMany(
        mappedBy = "person",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @JsonManagedReference("person-addresses")
    private List<Address> addresses = new ArrayList<>();
    
    /**
     * OneToMany zu Orders
     * Gleiche Konfiguration wie bei Addresses
     */
    @OneToMany(
        mappedBy = "person",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @JsonManagedReference("person-orders")
    private List<Order> orders = new ArrayList<>();

    public Person() {
    }
    
    

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    
    
    
    
    /**
     * Helper-Methode zum Hinzufügen einer Adresse
     * Synchronisiert beide Seiten der bidirektionalen Relationship
     * 
     * @param address die hinzuzufügende Adresse
     */
    public void addAddress(Address address) {
        addresses.add(address);
        address.setPerson(this);
    }
    
    /**
     * Helper-Methode zum Entfernen einer Adresse
     * Synchronisiert beide Seiten der bidirektionalen Relationship
     * 
     * @param address die zu entfernende Adresse
     */
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setPerson(null);
    }
    
    /**
     * Helper-Methode zum Hinzufügen einer Order
     * Synchronisiert beide Seiten der bidirektionalen Relationship
     * 
     * @param order die hinzuzufügende Order
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setPerson(this);
    }
    
    /**
     * Helper-Methode zum Entfernen einer Order
     * Synchronisiert beide Seiten der bidirektionalen Relationship
     * 
     * @param order die zu entfernende Order
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setPerson(null);
    }
}
