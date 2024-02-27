package com.example.demo.person;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonSpecification implements Specification<Person>{

    private PersonSearchDTO input;

    @Override
    @Nullable
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        UUID id = input.getId();
        if (id != null) {
            predicate = cb.and(predicate,
                cb.equal(root.get("id"), id));
        }

        String firstName = input.getFirstName();
        if (firstName != null && !firstName.isEmpty()) {
            predicate = cb.and(predicate, 
                cb.like(root.get("firstName"), firstName + "%"));
        }

        String lastName = input.getLastName();
        if (input.getLastName() != null) {
            predicate = cb.and(predicate, 
                cb.like(root.get("lastName"), lastName + "%"));
        }

        return predicate;
    }
    
}
