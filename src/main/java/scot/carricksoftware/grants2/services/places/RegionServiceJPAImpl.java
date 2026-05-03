/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.mappers.places.RegionMapper;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class RegionServiceJPAImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public List<RegionDTO> listRegions(String name) {
        List<Region> regionList;

        if (StringUtils.hasText(name)) {
            regionList = listRegionsByName(name);
        } else {
            regionList = regionRepository.findAll();
        }

        return regionList
                .stream()
                .map(regionMapper::regionToRegionDto)
                .collect(Collectors.toList());
    }

    private List<Region> listRegionsByName(String name) {
        return regionRepository.findAllByNameIsLikeIgnoreCase("%" + name+ "%");
    }

    @Override
    public Optional<RegionDTO> getRegionById(UUID id) {
        return Optional.ofNullable(regionMapper
                .regionToRegionDto(regionRepository
                .findById(id)
                .orElse(null)));
    }

    @Override
    public RegionDTO saveNewRegion(RegionDTO regionDTO) {
        return regionMapper
                .regionToRegionDto(regionRepository
                        .save(regionMapper.regionDtoToRegion(regionDTO)));
    }

    @Override
    public Optional<RegionDTO> updateRegionById(UUID id, RegionDTO regionDTO) {
        AtomicReference<Optional<RegionDTO>> atomicReference = new AtomicReference<>();

        regionRepository.findById(id).ifPresentOrElse(foundRegion -> {
            foundRegion.setName(regionDTO.getName());
            foundRegion.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(regionMapper
                    .regionToRegionDto(regionRepository.save(foundRegion))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteRegionById(UUID id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
