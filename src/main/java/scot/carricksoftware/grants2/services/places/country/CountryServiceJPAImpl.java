/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.country;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.mappers.places.CountryMapper;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class CountryServiceJPAImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final BuildPageRequest buildPageRequest;

    @Override
    public Page<CountryDTO> listCountries(String name, Integer pageNumber, Integer pageSize) {

        Sort sort = Sort.by(Sort.Order.asc("name"));
        PageRequest pageRequest = buildPageRequest.buildPageRequest(pageNumber, pageSize, sort);
        Page<Country> countryPage;

        if (StringUtils.hasText(name)) {
            countryPage = listCountriesByName(name, pageRequest);
        } else {
            countryPage = countryRepository.findAll(pageRequest);
        }

        return countryPage.map(countryMapper::countryToCountryDto);
    }

    private Page<Country> listCountriesByName(String name, Pageable pageable) {

        return countryRepository.findAllByNameIsLikeIgnoreCase("%" + name + "%", pageable);
    }

    @Override
    public Optional<CountryDTO> getCountryById(UUID id) {
        return Optional.ofNullable(countryMapper
                .countryToCountryDto(countryRepository
                        .findById(id)
                        .orElse(null)));
    }

    @Override
    public CountryDTO saveNewCountry(CountryDTO countryDTO) {
        return countryMapper.countryToCountryDto(countryRepository.save(countryMapper.countryDtoToCountry(countryDTO)));
    }

    @Override
    public Optional<CountryDTO> updateCountryById(UUID id, CountryDTO countryDTO) {
        AtomicReference<Optional<CountryDTO>> atomicReference = new AtomicReference<>();

        countryRepository.findById(id).ifPresentOrElse(foundCountry -> {
            foundCountry.setName(countryDTO.getName());
            foundCountry.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(countryMapper
                    .countryToCountryDto(countryRepository.save(foundCountry))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCountryById(UUID id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
