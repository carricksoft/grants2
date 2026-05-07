/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories.places;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import scot.carricksoftware.grants2.entities.places.Region;

import java.util.UUID;

@SuppressWarnings("unused")
public interface RegionRepository extends JpaRepository<Region, UUID> {
    Page<Region> findAllByNameIsLikeIgnoreCase(String region , Pageable pageable);

    @SuppressWarnings("NullableProblems")
    Page<Region> findAll(Pageable pageable);

}
