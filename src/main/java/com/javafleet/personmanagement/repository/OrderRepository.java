package com.javafleet.personmanagement.repository;

import com.javafleet.personmanagement.entity.Order;
import com.javafleet.personmanagement.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository für Order mit Query Methods
 * 
 * @author Elyndra Valen
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Finde Orders nach Status
     * Spring Data JPA generiert: SELECT * FROM orders WHERE status = ?
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Finde Orders nach Person ID
     * Spring Data JPA generiert: SELECT * FROM orders WHERE person_id = ?
     */
    List<Order> findByPersonId(Long personId);
    
    /**
     * Finde Orders nach Person Email
     * Navigiert durch die Relationship mit Unterstrich!
     * Spring Data JPA generiert: SELECT o.* FROM orders o INNER JOIN persons p ON o.person_id = p.id WHERE p.email = ?
     */
    List<Order> findByPersonEmail(String email);
    
    /**
     * Finde Orders wo Preis größer als
     * Spring Data JPA generiert: SELECT * FROM orders WHERE price > ?
     */
    List<Order> findByPriceGreaterThan(BigDecimal price);
    
    /**
     * Finde Orders nach Status, sortiere nach Datum absteigend
     * Spring Data JPA generiert: SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC
     */
    List<Order> findByStatusOrderByOrderDateDesc(OrderStatus status);
    
    /**
     * Finde Orders nach Person ID und Status
     * Spring Data JPA generiert: SELECT * FROM orders WHERE person_id = ? AND status = ?
     */
    List<Order> findByPersonIdAndStatus(Long personId, OrderStatus status);
    
    /**
     * Finde die neuesten 10 Orders
     * Spring Data JPA generiert: SELECT * FROM orders ORDER BY order_date DESC LIMIT 10
     */
    List<Order> findTop10ByOrderByOrderDateDesc();
    
    /**
     * Custom Query mit Fetch Join
     * Lädt Orders MIT Person in EINER Query
     */
    @Query("SELECT o FROM Order o JOIN FETCH o.person WHERE o.status = :status")
    List<Order> findByStatusWithPerson(@Param("status") OrderStatus status);
}
