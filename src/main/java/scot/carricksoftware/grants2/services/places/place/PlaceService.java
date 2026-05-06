/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.place;

import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.model.places.PlaceDTO;

import java.util.Optional;
import java.util.UUID;


public interface PlaceService {

    @SuppressWarnings("unused")
    Page<PlaceDTO> listPlaces(String place, Integer pageNumber, Integer pageSize);

    @SuppressWarnings("unused")
    Optional<PlaceDTO> getPlaceById(UUID id);

    @SuppressWarnings("unused")
    PlaceDTO saveNewPlace(PlaceDTO placeDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deletePlaceById(UUID id);

    @SuppressWarnings("unused")
    Optional<PlaceDTO> updatePlaceById(UUID id, PlaceDTO placeDTO);

}



