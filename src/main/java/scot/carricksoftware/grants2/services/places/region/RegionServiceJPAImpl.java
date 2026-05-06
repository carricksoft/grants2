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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class RegionServiceJPAImpl implements RegionService {

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public Page<RegionDTO> listRegions(String name, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Region> regionPage;


        if (StringUtils.hasText(name)) {
            regionPage = listRegionsByName(name, pageRequest);
        } else {
            regionPage = regionRepository.findAll(pageRequest);
        }

        return regionPage.map(regionMapper::regionToRegionDto);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = DEFAULT_PAGE_SIZE;
            } else {
                queryPageSize = pageSize;
            }
        }
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
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
