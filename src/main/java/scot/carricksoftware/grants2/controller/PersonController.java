/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.model.PersonDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;



@Slf4j
@AllArgsConstructor
@RestController
public class PersonController {
    private final PersonService personService;

    public static final String PERSON_PATH = "/people";
    public static final String PERSON_PATH_ID = PERSON_PATH + "/{id}";

    @GetMapping(PERSON_PATH)
    public List<PersonDTO> listPeople(){
        log.debug("PersonService::listPeople");
        return personService.listPeople();
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.debug("PersonService::handleNotFoundException");
        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("rawtypes")
    @PatchMapping(PERSON_PATH_ID)
    public ResponseEntity patchById(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        log.debug("PersonService::patchById");
        personService.patchById(id, personDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(PERSON_PATH_ID)
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        log.debug("PersonService::updateById");
        personService.updateById(id, personDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(PERSON_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id){
        log.debug("PersonService::deleteById");
        personService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(PERSON_PATH_ID)
    public PersonDTO getPersonById(@PathVariable UUID id){
        log.debug("PersonService::getPersonById");
        return personService.getPersonById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping(PERSON_PATH)
    public ResponseEntity handlePost(@RequestBody PersonDTO personDTO) {
        log.debug("PersonService::handlePost");
        PersonDTO savedPersonDTO = personService.saveNewPerson(personDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",PERSON_PATH + "/"  + savedPersonDTO.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



}
