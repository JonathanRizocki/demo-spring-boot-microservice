package com.example.demo.globalexceptions;

public class RequiredFieldException extends RuntimeException{

    public RequiredFieldException(String listOfFieldsMissing) {
        super(String.format("The following fields are required: %s", listOfFieldsMissing));
    }
    
}