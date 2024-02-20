package com.example.demo.Person;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    public List<PersonDTO> findAll() {
        List<Person> i = repository.findAll();
        List<PersonDTO> o = i.stream()
        .map(entity -> toPersonDTO(entity)).collect(Collectors.toList());
        return o;
    }

    public PersonDTO createPerson(PersonDTO p) {
        Person i = toPersonEntity(p);
        if (i == null) {
            throw new RuntimeException("null entity");
        }
        Person response = repository.save(i);
        PersonDTO o = toPersonDTO(response);
        return o;
    }

    public PersonDTO toPersonDTO(Person i) {
        PersonDTO o = PersonDTO.builder()
        .id(i.getId())
        .firstName(i.getFirstName())
        .lastName(i.getLastName())
        .build();
        return o;
    }

    public Person toPersonEntity(PersonDTO i) {
        Person o = Person.builder()
        .id(i.getId())
        .firstName(i.getFirstName())
        .lastName(i.getLastName())
        .build();
        return o;
    }
}
