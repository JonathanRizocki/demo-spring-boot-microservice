package com.example.demo.person;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonResource {

    private final PersonService service;

	@PostMapping("")
	public ResponseEntity<PersonDTO> createResource(@RequestBody PersonDTO p) {
		PersonDTO output = service.createPerson(p);
		return ResponseEntity.ok(output);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> getResource(@PathVariable UUID id) {
		PersonDTO p = service.getPersonById(id);
		return ResponseEntity.ok(p);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PersonDTO> updateResource(
			@PathVariable UUID id, 
			@RequestBody PersonDTO delta) {
		PersonDTO p = service.updatePersonByID(id, delta);
		return ResponseEntity.ok(p);
	}

	@PostMapping("/search")
    public ResponseEntity<Page<PersonDTO>> search(
		@RequestBody PersonSearchDTO request,
		@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

		if (page < 0) {
			page = 0;
		}
		if (size < 0 || size > 100) {
			size = 10;
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<PersonDTO> results = service.search(request, pageable);
		return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
}
