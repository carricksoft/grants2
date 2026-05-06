/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.person;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.mappers.PersonMapper;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;
import scot.carricksoftware.grants2.services.places.country.BuildPageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class PersonServiceJPAImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final BuildPageRequest buildPageRequest;

    @Override
    public Page<PersonDTO> listPeople(String firstName, String lastName, Integer pageNumber, Integer pageSize) {

        Sort sort = Sort.by(Sort.Order.asc("lastName"))
                .and(Sort.by(Sort.Order.asc("firstName")));
        PageRequest pageRequest = buildPageRequest.buildPageRequest(pageNumber, pageSize, sort);
        Page<Person> peoplePage;

        if (StringUtils.hasText(firstName) && lastName == null) {
            peoplePage = listPeopleByFirstName(firstName, pageRequest);
        } else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName)) {
            peoplePage = listPeopleByFirstAndLastName(firstName, lastName, pageRequest);
        } else if (StringUtils.hasText(lastName) && firstName == null) {
            peoplePage = listPeopleByLastName(lastName, pageRequest);}
        else {
            peoplePage = personRepository.findAll(pageRequest);
        }

        return peoplePage.map(personMapper::personToPersonDto);
    }

    private Page<Person> listPeopleByFirstAndLastName(String firstName, String lastName, Pageable pageable) {
        return personRepository
                .findAllByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase("%" + firstName + "%","%"+lastName+"%",pageable);
    }

    private Page<Person> listPeopleByFirstName(String firstName, Pageable pageable) {
        return personRepository.findAllByFirstNameIsLikeIgnoreCase("%" + firstName + "%", pageable);
    }

    private Page<Person> listPeopleByLastName(String lastName, Pageable pageable) {
        return personRepository.findAllByLastNameIsLikeIgnoreCase("%" + lastName + "%", pageable );
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
            foundPerson.setUpdatedDate(LocalDateTime.now());
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
