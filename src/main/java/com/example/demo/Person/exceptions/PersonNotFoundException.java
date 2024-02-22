package com.example.demo.person.exceptions;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException{

    PersonNotFoundException(UUID id) {
        super(String.format("Could not find person with id: %s", id));
    }
    
}
