/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.country;

import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.model.places.CountryDTO;

import java.util.Optional;
import java.util.UUID;

public interface CountryService {

    @SuppressWarnings("unused")
    Page<CountryDTO> listCountries(String name, Integer pageNumber, Integer pageSize);

    @SuppressWarnings("unused")
    Optional<CountryDTO> getCountryById(UUID id);

    @SuppressWarnings("unused")
    CountryDTO saveNewCountry(CountryDTO countryDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deleteCountryById(UUID id);

    @SuppressWarnings("unused")
    Optional<CountryDTO> updateCountryById(UUID id, CountryDTO countryDTO);

}



