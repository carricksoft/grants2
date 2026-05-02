/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scot.carricksoftware.grants2.model.places.CountryDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final Map<UUID, CountryDTO> countryMap;

    public CountryServiceImpl() {
        this.countryMap = new HashMap<>();

        CountryDTO countryDTO1 = CountryDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Scotland")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        CountryDTO countryDTO2 = CountryDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("England")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        CountryDTO countryDTO3 = CountryDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("France")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        
        countryMap.put(countryDTO1.getId(), countryDTO1);
        countryMap.put(countryDTO2.getId(), countryDTO2);
        countryMap.put(countryDTO3.getId(), countryDTO3);
    }

    @Override
    public List<CountryDTO> listCountries(){
        log.debug("CountryService::countryPeople");
        return new ArrayList<>(countryMap.values());
    }
    
    @Override
    public Optional<CountryDTO> getCountryById(UUID id) {
        log.debug("CountryService::getCountryById");

        return Optional.of(countryMap.get(id));
    }

    @Override
    public CountryDTO saveNewCountry(CountryDTO countryDTO) {
        log.debug("CountryService::saveNewCountry");
        CountryDTO savedCountryDTO = CountryDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .name(countryDTO.getName())
                .version(countryDTO.getVersion())
                .build();
        countryMap.put(savedCountryDTO.getId(), savedCountryDTO);
        return savedCountryDTO;
    }

    @Override
    public Boolean deleteCountryById(UUID id) {
        log.debug("CountryService::deleteById");
        countryMap.remove(id);
        return true;
    }

    @Override
    public Optional<CountryDTO> updateCountryById(UUID id, CountryDTO countryDTO) {
        log.debug("CountryService::upDateById");
        CountryDTO existingCountryDTO = countryMap.get(id);
        existingCountryDTO.setCreatedDate(countryDTO.getCreatedDate());
        existingCountryDTO.setName(countryDTO.getName());
        existingCountryDTO.setUpdatedDate(LocalDateTime.now());

        countryMap.put(existingCountryDTO.getId(), existingCountryDTO);
        return Optional.of(existingCountryDTO);

    }


}
