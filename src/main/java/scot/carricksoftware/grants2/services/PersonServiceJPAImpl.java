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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
        return Optional.ofNullable(personMapper
                .personToPersonDto(personRepository
                .findById(id)
                .orElse(null)));
    }

    @Override
    public PersonDTO saveNewPerson(PersonDTO personDTO) {
        return personMapper.personToPersonDto(personRepository.save(personMapper.personDtoToPerson(personDTO)));
    }

    @Override
    public Optional<PersonDTO> updatePersonById(UUID id, PersonDTO personDTO) {
        AtomicReference<Optional<PersonDTO>> atomicReference = new AtomicReference<>();

        personRepository.findById(id).ifPresentOrElse(foundPerson -> {
            foundPerson.setFirstName(personDTO.getFirstName());
            foundPerson.setLastName(personDTO.getLastName());
            foundPerson.setCertifiedYearOfBirth(personDTO.getCertifiedYearOfBirth());
            foundPerson.setRecordedYearOfBirth(personDTO.getRecordedYearOfBirth());
            foundPerson.setCertifiedYearOfDeath(personDTO.getCertifiedYearOfDeath());
            foundPerson.setUpdateDate(LocalDateTime.now());
            atomicReference.set(Optional.of(personMapper
                    .personToPersonDto(personRepository.save(foundPerson))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deletePersonById(UUID id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void patchPersonById(UUID id, PersonDTO personDTO) {

    }
}
