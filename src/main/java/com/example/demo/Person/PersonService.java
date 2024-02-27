package com.example.demo.person;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    PersonDTO createPerson(PersonDTO p);
    PersonDTO getPersonById(UUID id);
    PersonDTO updatePersonByID(UUID id, PersonDTO delta);
    Page<PersonDTO> search(PersonSearchDTO searchCriteria, Pageable pageable);
    
}
