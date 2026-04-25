package scot.carricksoftware.grants2.services;

import scot.carricksoftware.grants2.model.Beer;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface PersonService {

    List<Beer> listPeople();

    Beer getPersonById(UUID id);
}
