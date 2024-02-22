package com.example.demo.person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    List<PersonDTO> findAll();
    PersonDTO createPerson(PersonDTO p);
    PersonDTO getPersonById(UUID id);
    PersonDTO updatePersonByID(UUID id, PersonDTO delta);
    
}
