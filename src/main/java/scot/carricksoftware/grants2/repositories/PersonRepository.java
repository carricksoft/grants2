/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import scot.carricksoftware.grants2.entities.Person;

import java.util.UUID;

@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Page<Person> findAllByFirstNameIsLikeIgnoreCase(String firstName, Pageable pageable);
    Page<Person> findAllByLastNameIsLikeIgnoreCase(String lastName, Pageable pageable);
    Page<Person> findAllByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase(String firstName, String lastName, Pageable pageable);
}
