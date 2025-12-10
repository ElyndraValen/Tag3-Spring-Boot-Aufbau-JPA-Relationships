package com.javafleet.personmanagement.repository;

import com.javafleet.personmanagement.entity.AddressType;
import com.javafleet.personmanagement.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository für Person mit Query Methods und Custom Queries
 * 
 * @author Elyndra Valen
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    /**
     * Finde Person nach Email (exact match)
     * Spring Data JPA generiert: SELECT * FROM persons WHERE email = ?
     */
    Optional<Person> findByEmail(String email);
    
    /**
     * Finde Personen nach Nachname (exact match)
     * Spring Data JPA generiert: SELECT * FROM persons WHERE lastname = ?
     */
    List<Person> findByLastname(String lastname);
    
    /**
     * Finde Personen nach Vorname (case-insensitive, partial match)
     * Spring Data JPA generiert: SELECT * FROM persons WHERE LOWER(firstname) LIKE LOWER(CONCAT('%', ?, '%'))
     */
    List<Person> findByFirstnameContainingIgnoreCase(String firstname);
    
    /**
     * Finde Personen nach Vor- und Nachname
     * Spring Data JPA generiert: SELECT * FROM persons WHERE firstname = ? AND lastname = ?
     */
    List<Person> findByFirstnameAndLastname(String firstname, String lastname);
    
    /**
     * Finde Personen nach Nachname, sortiere nach Vorname aufsteigend
     * Spring Data JPA generiert: SELECT * FROM persons WHERE lastname = ? ORDER BY firstname ASC
     */
    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
    
    /**
     * Finde Personen mit Adresse in bestimmter Stadt
     * Der Unterstrich (_) navigiert durch die Relationship!
     * Spring Data JPA generiert automatisch den JOIN:
     * SELECT DISTINCT p.* FROM persons p INNER JOIN addresses a ON p.id = a.person_id WHERE a.city = ?
     */
    List<Person> findByAddresses_City(String city);
    
    /**
     * Finde Personen mit bestimmtem Adresstyp
     * Spring Data JPA generiert automatisch den JOIN:
     * SELECT DISTINCT p.* FROM persons p INNER JOIN addresses a ON p.id = a.person_id WHERE a.type = ?
     */
    List<Person> findByAddresses_Type(AddressType type);
    
    /**
     * Custom Query mit JPQL und Fetch Join
     * Lädt alle Personen MIT ihren Adressen in EINER Query
     * Löst das N+1 Problem!
     */
    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.addresses")
    List<Person> findAllWithAddresses();
    
    /**
     * Custom Query: Finde Person mit allen Relationships (Adressen UND Orders)
     * Zwei Fetch Joins in einer Query
     */
    @Query("SELECT DISTINCT p FROM Person p " +
           "LEFT JOIN FETCH p.addresses " +
           "LEFT JOIN FETCH p.orders " +
           "WHERE p.id = :id")
    Optional<Person> findByIdWithAll(@Param("id") Long id);
}
