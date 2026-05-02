/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.services.places.PlaceService;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
public class PlaceController {
    private final PlaceService placeService;

    public static final String PLACE_PATH = "/place";
    public static final String PLACE_PATH_ID = PLACE_PATH + "/{id}";

    @GetMapping(PLACE_PATH)
    public List<PlaceDTO> listPlaces(){
        log.debug("PlaceController::listPlaces");
        return placeService.listPlaces();
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.debug("PlaceController::handleNotFoundException");
        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(PLACE_PATH_ID)
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody PlaceDTO placeDTO) {
        log.debug("PlaceController::updateById");
        if( placeService.updatePlaceById(id, placeDTO).isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(PLACE_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id){
        log.debug("PlaceOntroller::deleteById");
        if (!placeService.deletePlaceById(id)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(PLACE_PATH_ID)
    public PlaceDTO getPlaceById(@PathVariable UUID id){
        log.debug("PlaceController::getPlaceById");
        return placeService.getPlaceById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping(PLACE_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody PlaceDTO placeDTO) {
        log.debug("PlaceController::handlePost");
        PlaceDTO savedPlaceDTO = placeService.saveNewPlace(placeDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",PLACE_PATH + "/"  + savedPlaceDTO.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



}
