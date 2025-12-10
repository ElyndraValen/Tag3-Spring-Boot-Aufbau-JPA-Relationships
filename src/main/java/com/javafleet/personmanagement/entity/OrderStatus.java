package com.javafleet.personmanagement.entity;

/**
 * Enum für Order Status
 * 
 * @author Elyndra Valen
 */
public enum OrderStatus {
    /**
     * Wartend
     */
    PENDING,
    
    /**
     * Bestätigt
     */
    CONFIRMED,
    
    /**
     * Versandt
     */
    SHIPPED,
    
    /**
     * Zugestellt
     */
    DELIVERED,
    
    /**
     * Storniert
     */
    CANCELLED
}
