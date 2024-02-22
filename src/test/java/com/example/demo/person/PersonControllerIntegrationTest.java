package com.example.demo.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.person.PersonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PersonControllerIntegrationTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.2");
    
    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Test
    public void createPerson() {

        String url = "http://localhost:" + port + "/person";

        PersonDTO input = generatePersonDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<PersonDTO> requestEntity = new HttpEntity<>(input, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                url,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        PersonDTO output = deserailizePersonDTO(responseEntity.getBody());
        assertNotNull(output.getId());
        output.setId(null);
        assertEquals(input, output);

    }

    private PersonDTO generatePersonDTO() {
        UUID seed = UUID.randomUUID();
        PersonDTO o = PersonDTO.builder()
        .firstName("FirstName_" + seed)
        .lastName("LastName_" + seed)
        .build();
        return o;
    }

    private PersonDTO deserailizePersonDTO(String input) {
        PersonDTO output = new PersonDTO();
        try {
            output = objectMapper.readValue(input, PersonDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to process input");
        } 
        return output;
    }
    
}
