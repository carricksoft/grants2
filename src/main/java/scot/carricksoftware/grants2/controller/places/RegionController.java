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
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.services.places.RegionService;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
public class RegionController {
    private final RegionService regionService;

    public static final String REGION_PATH = "/region";
    public static final String REGION_PATH_ID = REGION_PATH + "/{id}";

    @GetMapping(REGION_PATH)
    public List<RegionDTO> listRegions(){
        log.debug("RegionController::listRegions");
        return regionService.listRegions();
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.debug("RegionController::handleNotFoundException");
        return ResponseEntity.notFound().build();
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(REGION_PATH_ID)
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody RegionDTO regionDTO) {
        log.debug("RegionController::updateById");
        if( regionService.updateRegionById(id, regionDTO).isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(REGION_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id){
        log.debug("RegionOntroller::deleteById");
        if (!regionService.deleteRegionById(id)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(REGION_PATH_ID)
    public RegionDTO getRegionById(@PathVariable UUID id){
        log.debug("RegionController::getRegionById");
        return regionService.getRegionById(id).orElseThrow(NotFoundException::new);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping(REGION_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody RegionDTO regionDTO) {
        log.debug("RegionController::handlePost");
        RegionDTO savedRegionDTO = regionService.saveNewRegion(regionDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",REGION_PATH + "/"  + savedRegionDTO.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }



}
