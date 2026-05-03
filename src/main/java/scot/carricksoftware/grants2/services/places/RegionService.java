/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface RegionService {

    @SuppressWarnings("unused")
    List<RegionDTO> listRegions(String name);

    @SuppressWarnings("unused")
    Optional<RegionDTO> getRegionById(UUID id);

    @SuppressWarnings("unused")
    RegionDTO saveNewRegion(RegionDTO regionDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deleteRegionById(UUID id);

    @SuppressWarnings("unused")
    Optional<RegionDTO> updateRegionById(UUID id, RegionDTO regionDTO);

}



