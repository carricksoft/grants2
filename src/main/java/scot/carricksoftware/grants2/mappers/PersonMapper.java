/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.mappers;

import org.mapstruct.Mapper;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.model.PersonDTO;

@Mapper
public interface PersonMapper {

    Person      personDtoToPerson(PersonDTO dto);
    PersonDTO   personToPersonDto(Person person);
}
