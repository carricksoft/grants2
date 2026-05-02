package scot.carricksoftware.grants2.services.places;

import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface RegionService {

    @SuppressWarnings("unused")
    List<RegionDTO> listRegions();

    @SuppressWarnings("unused")
    Optional<RegionDTO> getRegionById(UUID id);

    @SuppressWarnings("unused")
    RegionDTO saveNewRegion(RegionDTO regionDTO);

    @SuppressWarnings({"SameReturnValue", "unused"})
    Boolean deleteRegionById(UUID id);

    @SuppressWarnings("unused")
    Optional<RegionDTO> updateRegionById(UUID id, RegionDTO regionDTO);

}



