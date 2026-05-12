/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.region;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class RegionServiceImpl implements RegionService {

    private final Map<UUID, RegionDTO> regionMap;

    public RegionServiceImpl() {
        super();
        this.regionMap = new HashMap<>();

        RegionDTO regionDTO1 = RegionDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Midlothian")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        RegionDTO regionDTO2 = RegionDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Edinburgh")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        RegionDTO regionDTO3 = RegionDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Yorkshire")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        regionMap.put(regionDTO1.getId(), regionDTO1);
        regionMap.put(regionDTO2.getId(), regionDTO2);
        regionMap.put(regionDTO3.getId(), regionDTO3);
    }

    @Override
    public Page<RegionDTO> listRegions(String name,Integer pageNumber, Integer pageSize){
        log.debug("RegionService::regionCountries");
        return new PageImpl<>(new ArrayList<>(regionMap.values()));
    }
    
    @Override
    public Optional<RegionDTO> getRegionById(UUID id) {
        log.debug("RegionService::getRegionById");
        return Optional.of(regionMap.get(id));
    }

    @Override
    public RegionDTO saveNewRegion(RegionDTO regionDTO) {
        log.debug("RegionService::saveNewRegion");
        RegionDTO savedRegionDTO = RegionDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .name(regionDTO.getName())
                .version(regionDTO.getVersion())
                .build();
        regionMap.put(savedRegionDTO.getId(), savedRegionDTO);
        return savedRegionDTO;
    }

    @Override
    public Boolean deleteRegionById(UUID id) {
        log.debug("RegionService::deleteById");
        regionMap.remove(id);
        return true;
    }

    @Override
    public Optional<RegionDTO> updateRegionById(UUID id, RegionDTO regionDTO) {
        log.debug("RegionService::upDateById");
        RegionDTO existingRegionDTO = regionMap.get(id);
        existingRegionDTO.setCreatedDate(regionDTO.getCreatedDate());
        existingRegionDTO.setName(regionDTO.getName());

        regionMap.put(existingRegionDTO.getId(), existingRegionDTO);
        return Optional.of(existingRegionDTO);
    }


}
