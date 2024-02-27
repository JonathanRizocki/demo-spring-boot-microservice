package com.example.demo.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageablePerson {

    private List<PersonDTO> content;
    private int numberOfElements;
    private int pageSize;
    private int totalPages;
    
}
