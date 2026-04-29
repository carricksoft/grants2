package scot.carricksoftware.grants2.services;

import scot.carricksoftware.grants2.model.PersonDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface PersonService {

    List<PersonDTO> listPeople();

    Optional<PersonDTO> getPersonById(UUID id);

    PersonDTO saveNewPerson(PersonDTO personDTO);

    Optional<PersonDTO> updatePersonById(UUID id, PersonDTO personDTO);

    Boolean deletePersonById(UUID id);

    void patchPersonById(UUID id, PersonDTO personDTO);
}



