package scot.carricksoftware.grants2.services;

import scot.carricksoftware.grants2.model.Person;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface PersonService {

    List<Person> listPeople();

    Person getPersonById(UUID id);

    Person saveNewPerson(Person person);

    void updateById(UUID id, Person person);

    void deleteById(UUID id);

    void patchById(UUID id, Person person);
}
