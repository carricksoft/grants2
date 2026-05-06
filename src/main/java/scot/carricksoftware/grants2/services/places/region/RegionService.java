/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.region;

import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.util.Optional;
import java.util.UUID;


public interface RegionService {

    @SuppressWarnings("unused")
    Page<RegionDTO> listRegions(String name,Integer pageNumber, Integer pageSize);

    @SuppressWarnings("unused")
    Optional<RegionDTO> getRegionById(UUID id);

    @SuppressWarnings("unused")
    RegionDTO saveNewRegion(RegionDTO regionDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deleteRegionById(UUID id);

    @SuppressWarnings("unused")
    Optional<RegionDTO> updateRegionById(UUID id, RegionDTO regionDTO);

}



