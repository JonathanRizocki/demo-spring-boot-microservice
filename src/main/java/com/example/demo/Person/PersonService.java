package com.example.demo.Person;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();
    PersonDTO createPerson(PersonDTO p);
    
}
