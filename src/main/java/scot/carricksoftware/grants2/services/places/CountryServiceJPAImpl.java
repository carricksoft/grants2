/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.mappers.places.CountryMapper;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CountryServiceJPAImpl implements CountryService {

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public Page<CountryDTO> listCountries(String name, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Country> countryPage;

        if (StringUtils.hasText(name)) {
            countryPage = listCountriesByName(name, pageRequest);
        } else {
            countryPage = countryRepository.findAll(pageRequest);
        }

        var z = countryPage;
        return countryPage.map(countryMapper::countryToCountryDto);

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
        var z = PageRequest.of(queryPageNumber, queryPageSize);
        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    private Page<Country> listCountriesByName(String name, PageRequest pageRequest) {
        Page<Country> z = countryRepository.findAllByNameIsLikeIgnoreCase("%" + name + "%", pageRequest);
        return countryRepository.findAllByNameIsLikeIgnoreCase("%" + name + "%", pageRequest);
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
