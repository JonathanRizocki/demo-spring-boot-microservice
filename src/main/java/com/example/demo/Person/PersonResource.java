package com.example.demo.Person;


import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonResource {

    private final PersonService service;

	@PostMapping("")
	public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO p) {
		PersonDTO output = service.createPerson(p);
		return ResponseEntity.ok(output);
	}

	@GetMapping("/list")
	public ResponseEntity<List<PersonDTO>> greeting() {
		List<PersonDTO> p = service.findAll();
		return ResponseEntity.ok(p);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> getPerson(@PathVariable UUID id) {
		PersonDTO p = service.getPersonById(id);
		return ResponseEntity.ok(p);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable UUID id, PersonDTO delta) {
		PersonDTO p = service.updatePersonByID(id, delta);
		return ResponseEntity.ok(p);
	}
    
}
