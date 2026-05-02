/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scot.carricksoftware.grants2.model.places.PlaceDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class PlaceServiceImpl implements PlaceService {

    private final Map<UUID, PlaceDTO> placeMap;

    public PlaceServiceImpl() {
        this.placeMap = new HashMap<>();

        PlaceDTO placeDTO1 = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Scotland")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        PlaceDTO placeDTO2 = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("England")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        PlaceDTO placeDTO3 = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("France")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        
        placeMap.put(placeDTO1.getId(), placeDTO1);
        placeMap.put(placeDTO2.getId(), placeDTO2);
        placeMap.put(placeDTO3.getId(), placeDTO3);
    }

    @Override
    public List<PlaceDTO> listPlaces(){
        log.debug("PlaceService::placePeople");
        return new ArrayList<>(placeMap.values());
    }
    
    @Override
    public Optional<PlaceDTO> getPlaceById(UUID id) {
        log.debug("PlaceService::getPlaceById");

        return Optional.of(placeMap.get(id));
    }

    @Override
    public PlaceDTO saveNewPlace(PlaceDTO placeDTO) {
        log.debug("PlaceService::saveNewPlace");
        PlaceDTO savedPlaceDTO = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .name(placeDTO.getName())
                .version(placeDTO.getVersion())
                .build();
        placeMap.put(savedPlaceDTO.getId(), savedPlaceDTO);
        return savedPlaceDTO;
    }

    @Override
    public Boolean deletePlaceById(UUID id) {
        log.debug("PlaceService::deleteById");
        placeMap.remove(id);
        return true;
    }

    @Override
    public Optional<PlaceDTO> updatePlaceById(UUID id, PlaceDTO placeDTO) {
        log.debug("PlaceService::upDateById");
        PlaceDTO existingPlaceDTO = placeMap.get(id);
        existingPlaceDTO.setCreatedDate(placeDTO.getCreatedDate());
        existingPlaceDTO.setName(placeDTO.getName());
        existingPlaceDTO.setUpdatedDate(LocalDateTime.now());

        placeMap.put(existingPlaceDTO.getId(), existingPlaceDTO);
        return Optional.of(existingPlaceDTO);

    }


}
