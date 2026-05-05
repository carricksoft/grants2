package scot.carricksoftware.grants2.services;

import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.model.PersonDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface PersonService {

    Page<PersonDTO> listPeople(String firstName, String lastName, Integer pageNumber, Integer pageSize);

    Optional<PersonDTO> getPersonById(UUID id);

    PersonDTO saveNewPerson(PersonDTO personDTO);

    Optional<PersonDTO> updatePersonById(UUID id, PersonDTO personDTO);

    Boolean deletePersonById(UUID id);

    void patchPersonById(UUID id, PersonDTO personDTO);
}



