/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import scot.carricksoftware.grants2.entities.Person;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findAllByFirstNameIsLikeIgnoreCase(String firstName);
}
