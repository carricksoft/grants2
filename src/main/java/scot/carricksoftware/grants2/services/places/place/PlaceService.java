/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.place;

import scot.carricksoftware.grants2.model.places.PlaceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PlaceService {

    @SuppressWarnings("unused")
    List<PlaceDTO> listPlaces(String place);

    @SuppressWarnings("unused")
    Optional<PlaceDTO> getPlaceById(UUID id);

    @SuppressWarnings("unused")
    PlaceDTO saveNewPlace(PlaceDTO placeDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deletePlaceById(UUID id);

    @SuppressWarnings("unused")
    Optional<PlaceDTO> updatePlaceById(UUID id, PlaceDTO placeDTO);

}



