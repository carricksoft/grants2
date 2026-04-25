/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import scot.carricksoftware.grants2.services.BeerService;
import scot.carricksoftware.grants2.model.Beer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
public class PersonController {
    private final BeerService beerService;

    @RequestMapping("/people")
    public List<Beer> listBeers(){
        log.debug("PersonService::ListPeople");
        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id){
        log.debug("PersonService::GetPersonById");
        return beerService.getBeerById(id);
    }

}
