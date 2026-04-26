package scot.carricksoftware.grants2.services;

import scot.carricksoftware.grants2.model.Person;
import scot.carricksoftware.grants2.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Person person2 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Person person3 = Person.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
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
