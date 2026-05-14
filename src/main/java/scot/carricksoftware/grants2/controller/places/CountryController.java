/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.services.places.country.CountryService;

import java.util.UUID;


@SuppressWarnings("ClassHasNoToStringMethod")
@Slf4j
@AllArgsConstructor
@RestController
public class CountryController {
    private final CountryService countryService;

    public static final String COUNTRY_PATH = "/countries";
    public static final String COUNTRY_PATH_ID = COUNTRY_PATH + "/{id}";

    @GetMapping(COUNTRY_PATH)
    public Page<CountryDTO> listCountries(@RequestParam(required = false) String name,
                                          Integer pageNumber,
                                          Integer pageSize){
        log.debug("CountryCountroller::listCountries");
        return countryService.listCountries(name, pageNumber, pageSize);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.debug("CountryController::handleNotFoundException");
        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(COUNTRY_PATH_ID)
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody CountryDTO countryDTO) {
        log.debug("CountryController::updateById");
        if( countryService.updateCountryById(id, countryDTO).isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(COUNTRY_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id){
        log.debug("CountryOntroller::deleteById");
        if (!countryService.deleteCountryById(id)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(COUNTRY_PATH_ID)
    public CountryDTO getCountryById(@PathVariable UUID id){
        log.debug("CountryController::getCountryById");
        return countryService.getCountryById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping(COUNTRY_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody CountryDTO countryDTO) {
        log.debug("CountryController::handlePost");
        CountryDTO savedCountryDTO = countryService.saveNewCountry(countryDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",COUNTRY_PATH + "/"  + savedCountryDTO.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



}
