package scot.carricksoftware.grants2.services.places;

import scot.carricksoftware.grants2.model.places.CountryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface CountryService {

    List<CountryDTO> listCountry();

    Optional<CountryDTO> getCountryById(UUID id);

    CountryDTO saveNewCountry(CountryDTO countryDTO);

    Boolean deleteCountryById(UUID id);

}



