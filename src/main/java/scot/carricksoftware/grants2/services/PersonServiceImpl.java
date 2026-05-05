package scot.carricksoftware.grants2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;
import scot.carricksoftware.grants2.model.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final Map<UUID, PersonDTO> personMap;

    public PersonServiceImpl() {
        this.personMap = new HashMap<>();

        PersonDTO personDTO1 = PersonDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Andrew Peter")
                .lastName("Grant")
                .certifiedYearOfBirth("1953")
                .recordedYearOfBirth("1953")
                .certifiedYearOfDeath("2030")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        PersonDTO personDTO2 = PersonDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Andrew James")
                .lastName("Grant")
                .recordedYearOfBirth("1975")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        PersonDTO personDTO3 = PersonDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .firstName("Abigail Elizabeth")
                .lastName("Grant")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        personMap.put(personDTO1.getId(), personDTO1);
        personMap.put(personDTO2.getId(), personDTO2);
        personMap.put(personDTO3.getId(), personDTO3);
    }

    @Override
    public Page<PersonDTO> listPeople(String firstName, String lastName, Integer pageNumber, Integer pageSize){
        log.debug("PersonService::listPeople");
        return new PageImpl<>(new ArrayList<>(personMap.values()))
    }

    @Override
    public Optional<PersonDTO> getPersonById(UUID id) {
        log.debug("PersonService::getPersonById");

        return Optional.of(personMap.get(id));
    }

    @Override
    public PersonDTO saveNewPerson(PersonDTO personDTO) {
        log.debug("PersonService::saveNewPerson");
        PersonDTO savedPersonDTO = PersonDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .certifiedYearOfDeath(personDTO.getCertifiedYearOfDeath())
                .recordedYearOfBirth(personDTO.getRecordedYearOfBirth())
                .certifiedYearOfDeath(personDTO.getCertifiedYearOfDeath())
                .version(personDTO.getVersion())
                .build();
        personMap.put(savedPersonDTO.getId(), savedPersonDTO);
        return savedPersonDTO;
    }

    @Override
    public Optional<PersonDTO> updatePersonById(UUID id, PersonDTO personDTO) {
        log.debug("PersonService::upDateById");
        PersonDTO existingPersonDTO = personMap.get(id);
        existingPersonDTO.setCertifiedYearOfBirth(personDTO.getCertifiedYearOfBirth());
        existingPersonDTO.setCertifiedYearOfDeath(personDTO.getCertifiedYearOfDeath());
        existingPersonDTO.setCreatedDate(personDTO.getCreatedDate());
        existingPersonDTO.setFirstName(personDTO.getFirstName());
        existingPersonDTO.setLastName(personDTO.getLastName());
        existingPersonDTO.setRecordedYearOfBirth(personDTO.getRecordedYearOfBirth());
        existingPersonDTO.setUpdatedDate(LocalDateTime.now());

        personMap.put(existingPersonDTO.getId(), existingPersonDTO);
        return Optional.of(existingPersonDTO);

    }

    @Override
    public Boolean deletePersonById(UUID id) {
        log.debug("PersonService::deleteById");
        personMap.remove(id);
        return true;
    }

    @Override
    public void patchPersonById(UUID id, PersonDTO personDTO) {
        log.debug("PersonService::patchById");
        PersonDTO existingPersonDTO = personMap.get(id);
        if (StringUtils.hasText(personDTO.getCertifiedYearOfBirth())) {
            existingPersonDTO.setCertifiedYearOfBirth(personDTO.getCertifiedYearOfBirth());
        }

        if (StringUtils.hasText(personDTO.getCertifiedYearOfDeath())) {
            existingPersonDTO.setCertifiedYearOfDeath(personDTO.getCertifiedYearOfDeath());
        }

        if (StringUtils.hasText(personDTO.getFirstName())) {
            existingPersonDTO.setFirstName(personDTO.getFirstName());
        }
        if (StringUtils.hasText(personDTO.getLastName())) {
            existingPersonDTO.setLastName(personDTO.getLastName());
        }
        if (StringUtils.hasText(personDTO.getRecordedYearOfBirth())) {
            existingPersonDTO.setRecordedYearOfBirth(personDTO.getRecordedYearOfBirth());
        }
        existingPersonDTO.setUpdatedDate(LocalDateTime.now());
        personMap.put(existingPersonDTO.getId(), existingPersonDTO);
    }
}
