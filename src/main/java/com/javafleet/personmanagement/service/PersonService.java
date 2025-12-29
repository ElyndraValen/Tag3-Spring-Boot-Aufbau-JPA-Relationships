/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javafleet.personmanagement.service;

import com.javafleet.personmanagement.entity.Address;
import com.javafleet.personmanagement.entity.Person;
import com.javafleet.personmanagement.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

/**
 *
 * @author trainer
 */
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getPersons() {
        List<Person> lp = new ArrayList<>();
        if (personRepository.count() <= 0) {
            Faker f = new Faker();
            for (int i = 0; i < 100; i++) {

                Person p = new Person(f.name().firstName(), f.name().lastName());
                p.setEmail(p.getFirstname() + "." + p.getLastname() + "@" + f.internet().domainName() + f.internet().domainSuffix());
                p.addAddress(new Address(f.address().streetName(), f.address().cityName(),
                        f.address().zipCode(), f.address().country()));
                p.addAddress(new Address(f.address().streetName(), f.address().cityName(),
                        f.address().zipCode(), f.address().country()));
                lp.add(p);
                personRepository.save(p);
            }
            return  lp;
        }
        return personRepository.findAll();
    }
}
