/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import scot.carricksoftware.grants2.mappers.places.PlaceMapper;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PlaceServiceJPAImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Override
    public List<PlaceDTO> listPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(placeMapper::placeToPlaceDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlaceDTO> getPlaceById(UUID id) {
        return Optional.ofNullable(placeMapper
                .placeToPlaceDto(placeRepository
                .findById(id)
                .orElse(null)));
    }

    @Override
    public PlaceDTO saveNewPlace(PlaceDTO placeDTO) {
        return placeMapper.placeToPlaceDto(placeRepository.save(placeMapper.placeDtoToPlace(placeDTO)));
    }

    @Override
    public Optional<PlaceDTO> updatePlaceById(UUID id, PlaceDTO placeDTO) {
        AtomicReference<Optional<PlaceDTO>> atomicReference = new AtomicReference<>();

        placeRepository.findById(id).ifPresentOrElse(foundPlace -> {
            foundPlace.setName(placeDTO.getName());
            foundPlace.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(placeMapper
                    .placeToPlaceDto(placeRepository.save(foundPlace))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deletePlaceById(UUID id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
