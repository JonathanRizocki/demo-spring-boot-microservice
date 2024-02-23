package com.example.demo.person.exceptions;

public class RequiredFieldException extends RuntimeException{

    public RequiredFieldException(String listOfFieldsMissing) {
        super(String.format("The following fields are required: %s", listOfFieldsMissing));
    }
    
}