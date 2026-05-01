/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.mappers.places;

import org.mapstruct.Mapper;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.model.places.CountryDTO;

@Mapper
public interface CountryMapper {

    @SuppressWarnings("unused")
    Country countryDtoToCountry(CountryDTO dto);
    @SuppressWarnings("unused")
    CountryDTO countryToCountryDto(Country country);
}
