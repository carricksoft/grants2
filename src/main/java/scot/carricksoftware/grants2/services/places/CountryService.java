package scot.carricksoftware.grants2.services.places;

import scot.carricksoftware.grants2.model.places.CountryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface CountryService {

    @SuppressWarnings("unused")
    List<CountryDTO> listCountries();

    @SuppressWarnings("unused")
    Optional<CountryDTO> getCountryById(UUID id);

    @SuppressWarnings("unused")
    CountryDTO saveNewCountry(CountryDTO countryDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deleteCountryById(UUID id);

    @SuppressWarnings("unused")
    Optional<CountryDTO> updateCountryById(UUID id, CountryDTO countryDTO);

}



