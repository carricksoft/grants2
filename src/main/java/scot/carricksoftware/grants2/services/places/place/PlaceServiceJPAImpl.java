/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.place;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.mappers.places.PlaceMapper;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;
import scot.carricksoftware.grants2.services.BuildPageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class PlaceServiceJPAImpl implements PlaceService {

    private final BuildPageRequest buildPageRequest;
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Override
    public Page<PlaceDTO> listPlaces(String name, Integer pageNumber, Integer pageSize) {

        Sort sort = Sort.by(Sort.Order.asc("name"));
        PageRequest pageRequest = buildPageRequest.buildPageRequest(pageNumber, pageSize, sort);
        Page<Place> placePage;

        if (StringUtils.hasText(name)) {
            placePage = listPlacesByName(name, pageRequest);
        } else {
            placePage = placeRepository.findAll(pageRequest);
        }

        return placePage.map(placeMapper::placeToPlaceDto);
    }

    private Page<Place> listPlacesByName(String name, Pageable pageable) {
        return placeRepository.findAllByNameIsLikeIgnoreCase("%" + name+ "%",pageable);
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
            atomicReference.set(Optional.of(placeMapper
                    .placeToPlaceDto(placeRepository.save(foundPlace))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deletePlaceById(UUID id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
        }
        return true;
    }

}
