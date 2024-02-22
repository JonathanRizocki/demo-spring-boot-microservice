package com.example.demo.person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final ModelMapper modelMapper;
    
    @Override
    public List<PersonDTO> findAll() {
        // TODO: Remove in favor of paginated response entity
        List<Person> i = repository.findAll();
        List<PersonDTO> o = i.stream()
        .map(entity -> convertToDTO(entity)).collect(Collectors.toList());
        return o;
    }

    @Override
    public PersonDTO createPerson(PersonDTO p) {
        Person i = convertToEntity(p);
        if (i == null) {
            throw new RuntimeException("null entity");
        }
        Person response = repository.save(i);
        PersonDTO o = convertToDTO(response);
        return o;
    }

    @Override
    public PersonDTO getPersonById(UUID id) {
        if (id == null) {
            // TODO: Proper Response Entity Error handling
            throw new RuntimeException("null ID");
        }

        Optional<Person> person = repository.findById(id);

        if (!person.isPresent()) {
            // TODO: Proper Response Entity Error handling
            throw new RuntimeException("Person not found");
        }
        return convertToDTO(person.get());
    }

    @Override
    public PersonDTO updatePersonByID(UUID id, PersonDTO delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePersonByID'");
    }

    public Person convertToEntity(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
