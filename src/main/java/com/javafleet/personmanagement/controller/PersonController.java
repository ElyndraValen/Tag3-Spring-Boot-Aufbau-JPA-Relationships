package com.javafleet.personmanagement.controller;

import com.javafleet.personmanagement.entity.Address;
import com.javafleet.personmanagement.entity.AddressType;
import com.javafleet.personmanagement.entity.Person;
import com.javafleet.personmanagement.repository.PersonRepository;
import com.javafleet.personmanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller für Person Management
 * 
 * @author Elyndra Valen
 */
@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    
    private final PersonRepository personRepository;
    
   private final PersonService personService;
    
    /**
     * Alle Personen abrufen
     * GET /api/persons
     */
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getPersons();
    }
    
    /**
     * Person mit ID abrufen
     * GET /api/persons/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Neue Person mit Adressen erstellen
     * POST /api/persons
     * Body: { "firstname": "Max", "lastname": "Mustermann", ... }
     */
    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        // Helper-Methoden synchronisieren die Relationships automatisch!
        return personRepository.save(person);
    }
    
    /**
     * Adresse zu existierender Person hinzufügen
     * POST /api/persons/{id}/addresses
     * Body: { "street": "...", "city": "...", ... }
     */
    @PostMapping("/{id}/addresses")
    public ResponseEntity<Person> addAddress(
            @PathVariable Long id,
            @RequestBody Address address) {
        
        return personRepository.findById(id)
            .map(person -> {
                person.addAddress(address);  // Helper-Methode synchronisiert!
                return ResponseEntity.ok(personRepository.save(person));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Suche nach Nachname
     * GET /api/persons/search/by-lastname?lastname=Mustermann
     */
    @GetMapping("/search/by-lastname")
    public List<Person> searchByLastname(@RequestParam String lastname) {
        return personRepository.findByLastname(lastname);
    }
    
    /**
     * Suche nach Email
     * GET /api/persons/search/by-email?email=max@example.com
     */
    @GetMapping("/search/by-email")
    public ResponseEntity<Person> searchByEmail(@RequestParam String email) {
        return personRepository.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Suche Personen mit Adresse in Stadt
     * GET /api/persons/search/by-city?city=Berlin
     */
    @GetMapping("/search/by-city")
    public List<Person> searchByCity(@RequestParam String city) {
        return personRepository.findByAddresses_City(city);
    }
    
    
     @GetMapping("/search/by-country")
    public List<Person> searchByCountry(@RequestParam String country) {
        return personRepository.findByAddresses_Country(country);
    }
    
    
    /**
     * Suche Personen mit Adresstyp
     * GET /api/persons/search/by-address-type?type=HOME
     */
    @GetMapping("/search/by-address-type")
    public List<Person> searchByAddressType(@RequestParam AddressType type) {
        return personRepository.findByAddresses_Type(type);
    }
    
    /**
     * Alle Personen MIT Adressen (Fetch Join - löst N+1 Problem)
     * GET /api/persons/with-addresses
     */
    @GetMapping("/with-addresses")
    public List<Person> getAllPersonsWithAddresses() {
        return personRepository.findAllWithAddresses();
    }
    
    @GetMapping("/create")
    public String create() {
        personService.getPersons();
        return "Datene Created!";
    }
    
}
