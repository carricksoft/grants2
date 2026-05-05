/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.model.PersonDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;



@Slf4j
@AllArgsConstructor
@RestController
public class PersonController {
    private final PersonService personService;

    public static final String PERSON_PATH = "/people";
    public static final String PERSON_PATH_ID = PERSON_PATH + "/{id}";

    @GetMapping(PERSON_PATH)
    public Page<PersonDTO> listPeople(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      Integer pageNumber,
                                      Integer pageSize){
        log.debug("PersonController::listPeople");
        return personService.listPeople(firstName, lastName, pageNumber, pageSize);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.debug("PersonController::handleNotFoundException");
        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("rawtypes")
    @PatchMapping(PERSON_PATH_ID)
    public ResponseEntity patchById(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        log.debug("PersonController::patchById");
        personService.patchPersonById(id, personDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(PERSON_PATH_ID)
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        log.debug("PersonController::updateById");
        if( personService.updatePersonById(id, personDTO).isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(PERSON_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id){
        log.debug("PersonController::deleteById");
        if (!personService.deletePersonById(id)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(PERSON_PATH_ID)
    public PersonDTO getPersonById(@PathVariable UUID id){
        log.debug("PersonController::getPersonById");
        return personService.getPersonById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping(PERSON_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody PersonDTO personDTO) {
        log.debug("PersonController::handlePost");
        PersonDTO savedPersonDTO = personService.saveNewPerson(personDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",PERSON_PATH + "/"  + savedPersonDTO.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



}
