/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.entities.Person;
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

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Page<PersonDTO> listPeople(String firstName, String lastName, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        List<Person> peopleList;

        if (StringUtils.hasText(firstName) && lastName == null) {
            peopleList = listPeopleByFirstName(firstName);
        } else if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName)) {
            peopleList = listPeopleByFirstAndLastName(firstName, lastName);
        } else if (StringUtils.hasText(lastName) && firstName == null) {
            peopleList = listPeopleByLastName(lastName);}
        else {
            peopleList = personRepository.findAll();
        }

        return peopleList
                .stream()
                .map(personMapper::personToPersonDto)
                .collect(Collectors.toList());
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = DEFAULT_PAGE_SIZE;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }


    private List<Person> listPeopleByFirstAndLastName(String firstName, String lastName) {
        return personRepository.findAllByFirstNameIsLikeIgnoreCaseAndLastNameIsLikeIgnoreCase("%" + firstName + "%","%"+lastName+"%");
    }

    private List<Person> listPeopleByFirstName(String firstName) {
        return personRepository.findAllByFirstNameIsLikeIgnoreCase("%" + firstName + "%");
    }

    private List<Person> listPeopleByLastName(String lastName) {
        return personRepository.findAllByLastNameIsLikeIgnoreCase("%" + lastName + "%");
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
