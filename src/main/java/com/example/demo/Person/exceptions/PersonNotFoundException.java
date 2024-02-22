package com.example.demo.person.exceptions;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(UUID id) {
        super(String.format("Could not find person with id: %s", id));
    }
    
}
