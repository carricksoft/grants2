/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.mappers.places;

import org.mapstruct.Mapper;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.model.places.PlaceDTO;

@Mapper
public interface PlaceMapper {

    Place placeDtoToPlace(PlaceDTO dto);
    PlaceDTO placeToPlaceDto(Place place);
}
