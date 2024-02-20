package com.example.demo.Person;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonResource {

    private final PersonService service;

	@PostMapping("")
	public ResponseEntity<PersonDTO> createPerson() {
		PersonDTO p = PersonDTO.builder()
		.firstName("FirstName")
		.lastName("LastName")
		.build();

		PersonDTO output = service.createPerson(p);
		return ResponseEntity.ok(output);
	}

	@GetMapping("/list")
	public ResponseEntity<List<PersonDTO>> greeting() {
		List<PersonDTO> p = service.findAll();

		return ResponseEntity.ok(p);
	}
    
}
