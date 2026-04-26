package scot.carricksoftware.grants2.services;

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

    private final Map<UUID, Person> beerMap;

    public PersonServiceImpl() {
        this.beerMap = new HashMap<>();

        Person person1 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Andrew Peter")
                .secondName("Grant")
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
                .secondName("Grant")
                .recordedYearOfBirth("1975")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Person person3 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Abigail Elizabeth")
                .secondName("Grant")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(person1.getId(), person1);
        beerMap.put(person2.getId(), person2);
        beerMap.put(person3.getId(), person3);
    }

    @Override
    public List<Person> listPeople(){
        log.debug("PersonService::ListPeople");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Person getPersonById(UUID id) {
        log.debug("PersonService::GetPersonById");

        return beerMap.get(id);
    }
}
