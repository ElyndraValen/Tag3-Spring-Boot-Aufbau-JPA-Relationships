package com.javafleet.personmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Application Class für Tag 3: JPA Relationships & Queries
 * 
 * @author Elyndra Valen
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.javafleet.*")
public class PersonManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonManagementApplication.class, args);
    }
}
