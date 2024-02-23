package com.example.demo.person;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import com.example.demo.globalexceptions.RequiredFieldException;
import com.example.demo.person.exceptions.PersonNotFoundException;

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
        requiredFieldId(id);
        @SuppressWarnings("null")
        Person o = repository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));
        return convertToDTO(o);
    }

    @Override
    public PersonDTO updatePersonByID(UUID id, PersonDTO changes) {
        requiredFieldId(id);
        @SuppressWarnings("null")
        Person target = repository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));

        Person delta = convertToEntity(changes);
        delta.setId(id);
        copyProperties(delta, target);

        @SuppressWarnings("null")
        Person result = repository.save(target);
        return convertToDTO(result);
    }

    private Person convertToEntity(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private void copyProperties(Person source, Person target) {
        // Use BeanUtils.copyProperties to copy non-null properties from source to target
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Person source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) 
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public void requiredFieldId(UUID id) {
        if (id == null) {
            throw new RequiredFieldException("id");
        }
    }
}
