package com.example.demo.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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

    private String createPersonURL;
    private String getPersonByIdURL;

    @BeforeEach
    public void setup() {
        String base = "http://localhost:" + port;
        createPersonURL =  base + "/person";
        getPersonByIdURL = createPersonURL + "/";
    }

    @Test
    public void createPerson() {
        PersonDTO input = generatePersonDTO();
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<PersonDTO> requestEntity = new HttpEntity<>(input, headers);
        ResponseEntity<String> responseEntity = createPersonPostRest(requestEntity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        PersonDTO output = deserailizePersonDTO(responseEntity.getBody());
        assertNotNull(output.getId());
        output.setId(null);
        assertEquals(input, output);
    }

    @Test
    public void getPersonById() {
        ResponseEntity<String> responseEntity = buildAndCreatePerson(Optional.empty());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        PersonDTO output = deserailizePersonDTO(responseEntity.getBody());

        responseEntity = getPersonByIdRest(output.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /*
     * Rest Endpoint Helper Functions
     */
    private ResponseEntity<String> createPersonPostRest(HttpEntity<PersonDTO> input) {
        return restTemplate.postForEntity(
            createPersonURL,
            input,
            String.class);
    }

    private ResponseEntity<String> getPersonByIdRest(UUID id) {
        return restTemplate.getForEntity(
            getPersonByIdURL + id, 
            String.class);
    }

    /*
     * Data Generation Helper Functions
     */
    private PersonDTO generatePersonDTO() {
        UUID seed = UUID.randomUUID();
        PersonDTO o = PersonDTO.builder()
        .firstName("FirstName_" + seed)
        .lastName("LastName_" + seed)
        .build();
        return o;
    }

    private PersonDTO deserailizePersonDTO(String i) {
        PersonDTO o = new PersonDTO();
        try {
            o = objectMapper.readValue(i, PersonDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to process input");
        } 
        return o;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders o = new HttpHeaders();
        o.set("Content-Type", "application/json");
        return o;
    }

    /*
     * Repetitive CRUD operation helper functions
     */
    private ResponseEntity<String> buildAndCreatePerson(Optional<PersonDTO> opt) {
        PersonDTO input = generatePersonDTO();
        if (opt != null && opt.isPresent()) {
            input = opt.get();
        }

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<PersonDTO> requestEntity = new HttpEntity<>(input, headers);
        return createPersonPostRest(requestEntity);
    }
    
}
