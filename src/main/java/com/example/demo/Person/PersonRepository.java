package com.example.demo.person;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PersonRepository extends JpaRepository<Person, UUID>,
 JpaSpecificationExecutor<Person>{
    
}
