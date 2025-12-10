package com.javafleet.personmanagement.controller;

import com.javafleet.personmanagement.entity.Order;
import com.javafleet.personmanagement.entity.OrderStatus;
import com.javafleet.personmanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller für Order Management
 * 
 * @author Elyndra Valen
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderRepository orderRepository;
    
    /**
     * Alle Orders abrufen
     * GET /api/orders
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    /**
     * Order mit ID abrufen
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Neue Order erstellen
     * POST /api/orders
     * Body: { "orderNumber": "...", "price": 99.99, ... }
     */
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
    
    /**
     * Suche Orders nach Status
     * GET /api/orders/search/by-status?status=PENDING
     */
    @GetMapping("/search/by-status")
    public List<Order> searchByStatus(@RequestParam OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    /**
     * Suche Orders einer Person
     * GET /api/orders/search/by-person?personId=1
     */
    @GetMapping("/search/by-person")
    public List<Order> searchByPerson(@RequestParam Long personId) {
        return orderRepository.findByPersonId(personId);
    }
    
    /**
     * Suche Orders nach Person Email
     * GET /api/orders/search/by-email?email=max@example.com
     */
    @GetMapping("/search/by-email")
    public List<Order> searchByEmail(@RequestParam String email) {
        return orderRepository.findByPersonEmail(email);
    }
    
    /**
     * Suche Orders teurer als Betrag
     * GET /api/orders/search/expensive?minPrice=100
     */
    @GetMapping("/search/expensive")
    public List<Order> searchExpensive(@RequestParam BigDecimal minPrice) {
        return orderRepository.findByPriceGreaterThan(minPrice);
    }
    
    /**
     * Neueste Orders
     * GET /api/orders/recent
     */
    @GetMapping("/recent")
    public List<Order> getRecentOrders() {
        return orderRepository.findTop10ByOrderByOrderDateDesc();
    }
}
