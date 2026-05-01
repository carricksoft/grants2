/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories.places;

import org.springframework.data.jpa.repository.JpaRepository;
import scot.carricksoftware.grants2.entities.places.Country;

import java.util.UUID;

@SuppressWarnings("unused")
public interface CountryRepository extends JpaRepository<Country, UUID> {
}
