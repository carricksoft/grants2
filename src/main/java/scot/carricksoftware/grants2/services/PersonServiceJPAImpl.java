/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import scot.carricksoftware.grants2.mappers.PersonMapper;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PersonServiceJPAImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public List<PersonDTO> listPeople() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::personToPersonDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonDTO> getPersonById(UUID id) {
        return Optional.ofNullable(personMapper.personToPersonDto(personRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public PersonDTO saveNewPerson(PersonDTO personDTO) {
        return null;
    }

    @Override
    public void updateById(UUID id, PersonDTO personDTO) {

    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchById(UUID id, PersonDTO personDTO) {

    }
}
