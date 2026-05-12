/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.region;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.mappers.places.RegionMapper;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;
import scot.carricksoftware.grants2.services.BuildPageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class RegionServiceJPAImpl implements RegionService {

    @SuppressWarnings("FieldNotUsedInToString")
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final BuildPageRequest buildPageRequest;

    @Override
    public Page<RegionDTO> listRegions(String name, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        PageRequest pageRequest = buildPageRequest.buildPageRequest(pageNumber, pageSize,sort);
        Page<Region> regionPage;

        if (StringUtils.hasText(name)) {
            regionPage = listRegionsByName(name, pageRequest);
        } else {
            regionPage = regionRepository.findAll(pageRequest);
        }

        return regionPage.map(regionMapper::regionToRegionDto);
    }

    private Page<Region> listRegionsByName(String name, Pageable pageable) {
        return regionRepository.findAllByNameIsLikeIgnoreCase("%" + name+ "%", pageable);
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
            atomicReference.set(Optional.of(regionMapper
                    .regionToRegionDto(regionRepository.save(foundRegion))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteRegionById(UUID id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegionServiceJPAImpl{" +
                "regionRepository=" + regionRepository +
                ", regionMapper=" + regionMapper +
                ", buildPageRequest=" + buildPageRequest +
                '}';
    }
}
