/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories.places;

import org.springframework.data.jpa.repository.JpaRepository;
import scot.carricksoftware.grants2.entities.places.Region;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface RegionRepository extends JpaRepository<Region, UUID> {
    List<Region> findAllByNameIsLikeIgnoreCase(String region);
}
