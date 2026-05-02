/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.mappers.places;

import org.mapstruct.Mapper;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.model.places.RegionDTO;

@Mapper
public interface RegionMapper {

    @SuppressWarnings("unused")
    Region regionDtoToRegion(RegionDTO dto);
    @SuppressWarnings("unused")
    RegionDTO regionToRegionDto(Region region);
}
