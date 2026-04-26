package scot.carricksoftware.grants2.services;

import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final Map<UUID, Person> personMap;

    public PersonServiceImpl() {
        this.personMap = new HashMap<>();

        Person person1 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Andrew Peter")
                .lastName("Grant")
                .certifiedYearOfBirth("1953")
                .recordedYearOfBirth("1953")
                .certifiedYearOfDeath("2030")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Person person2 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Andrew James")
                .lastName("Grant")
                .recordedYearOfBirth("1975")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Person person3 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Abigail Elizabeth")
                .lastName("Grant")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        personMap.put(person1.getId(), person1);
        personMap.put(person2.getId(), person2);
        personMap.put(person3.getId(), person3);
    }

    @Override
    public List<Person> listPeople(){
        log.debug("PersonService::listPeople");
        return new ArrayList<>(personMap.values());
    }

    @Override
    public Person getPersonById(UUID id) {
        log.debug("PersonService::getPersonById");

        return personMap.get(id);
    }

    @Override
    public Person saveNewPerson(Person person) {
        log.debug("PersonService::saveNewPerson");
        Person savedPerson = Person.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .certifiedYearOfDeath(person.getCertifiedYearOfDeath())
                .recordedYearOfBirth(person.getRecordedYearOfBirth())
                .certifiedYearOfDeath(person.getCertifiedYearOfDeath())
                .version(person.getVersion())
                .build();
        personMap.put(savedPerson.getId(), savedPerson);
        return savedPerson;
    }

    @Override
    public void updateById(UUID id, Person person) {
        log.debug("PersonService::upDateById");
        Person existingPerson = personMap.get(id);
        existingPerson.setCertifiedYearOfBirth(person.getCertifiedYearOfBirth());
        existingPerson.setCertifiedYearOfDeath(person.getCertifiedYearOfDeath());
        existingPerson.setCreatedDate(person.getCreatedDate());
        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setRecordedYearOfBirth(person.getRecordedYearOfBirth());
        existingPerson.setUpdateDate(LocalDateTime.now());

        personMap.put(existingPerson.getId(), existingPerson);

    }

    @Override
    public void deleteById(UUID id) {
        log.debug("PersonService::deleteById");
        personMap.remove(id);
    }

    @Override
    public void patchById(UUID id, Person person) {
        log.debug("PersonService::patchById");
        Person existingPerson = personMap.get(id);
        if (StringUtils.hasText(person.getCertifiedYearOfBirth())) {
            existingPerson.setCertifiedYearOfBirth(person.getCertifiedYearOfBirth());
        }

        if (StringUtils.hasText(person.getCertifiedYearOfDeath())) {
            existingPerson.setCertifiedYearOfDeath(person.getCertifiedYearOfDeath());
        }

        if (StringUtils.hasText(person.getFirstName())) {
            existingPerson.setFirstName(person.getFirstName());
        }
        if (StringUtils.hasText(person.getLastName())) {
            existingPerson.setLastName(person.getLastName());
        }
        if (StringUtils.hasText(person.getRecordedYearOfBirth())) {
            existingPerson.setRecordedYearOfBirth(person.getRecordedYearOfBirth());
        }
        existingPerson.setUpdateDate(LocalDateTime.now());
        personMap.put(existingPerson.getId(), existingPerson);
    }
}
