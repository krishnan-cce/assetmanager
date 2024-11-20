package com.udyata.linentrack.linentrack.serviceimpl.locationmaster;

import com.udyata.linentrack.linentrack.entity.locationmaster.LocationMaster;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.exception.LinenTrackApiException;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.mapper.LocationMasterMapper;
import com.udyata.linentrack.linentrack.payload.locationmasterdto.LocationMasterDTO;
import com.udyata.linentrack.linentrack.repository.locationmaster.LocationMasterRepository;
import com.udyata.linentrack.linentrack.security.SecurityUtils;
import com.udyata.linentrack.linentrack.service.locationmaster.LocationMasterService;
import com.udyata.linentrack.linentrack.service.log.LogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationMasterServiceImpl implements LocationMasterService {

    private final LocationMasterRepository locationMasterRepository;
    private final SecurityUtils securityUtils;
    private final LogService logService;

    public LocationMasterServiceImpl(LocationMasterRepository locationMasterRepository, SecurityUtils securityUtils,
                                     LogService logService
    ) {
        this.locationMasterRepository = locationMasterRepository;
        this.securityUtils = securityUtils;
        this.logService = logService;
    }

    @Override
    public LocationMasterDTO createLocationMaster(LocationMasterDTO locationMasterDTO) {
        try {
            LocationMaster locationMaster = mapToEntity(locationMasterDTO);
            LocationMaster savedLocationMaster = locationMasterRepository.save(locationMaster);
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "CREATE", "LocationMaster", savedLocationMaster.getId(), "LocationMaster Created Successfully");

            return LocationMasterMapper.INSTANCE.toDTO(savedLocationMaster);
        }catch (ResourceNotFoundException | LinenTrackApiException ex) {
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "ERROR", "LocationMaster", 0L, "Failed to create LocationMaster: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "ERROR", "LocationMaster", 0L, "Failed to create LocationMaster: " + ex.getMessage());
            throw new RuntimeException("An unexpected error occurred while creating LocationMaster", ex);
        }
    }

    @Override
    public LocationMasterDTO updateLocationMaster(LocationMasterDTO locationMasterDTO) {
        try {
            LocationMaster locationMaster = locationMasterRepository.findById(locationMasterDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("LocationMaster", "id", locationMasterDTO.getId()));

            LocationMasterMapper.INSTANCE.updateEntityFromDTO(locationMasterDTO, locationMaster);
            LocationMaster updatedLocationMaster = locationMasterRepository.save(locationMaster);
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "UPDATE", "LocationMaster", updatedLocationMaster.getId(), "LocationMaster Updated Successfully");

            return LocationMasterMapper.INSTANCE.toDTO(updatedLocationMaster);
        }catch (ResourceNotFoundException | LinenTrackApiException ex) {
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "ERROR", "LocationMaster", 0L, "Failed to update LocationMaster: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logService.logOperation(securityUtils.getCurrentAuthenticatedUser().getId(), "ERROR", "LinenCategoryMaster", 0L, "Failed to update LinenCategoryMaster: " + ex.getMessage());
            throw new RuntimeException("An unexpected error occurred while updating LinenCategoryMaster", ex);
        }
    }

    @Override
    public List<LocationMasterDTO> getLocationMasterList() {
        List<LocationMaster> locationMasters = locationMasterRepository.findAll();
        return locationMasters.stream().map(LocationMasterMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    private LocationMaster mapToEntity(LocationMasterDTO locationMasterDTO) {
        User currentUser = securityUtils.getCurrentAuthenticatedUser();
        LocationMaster locationMaster = new LocationMaster();

        locationMaster.setName(locationMasterDTO.getName());
        locationMaster.setCode(locationMasterDTO.getCode());
        locationMaster.setAddedBy(currentUser.getId());
        locationMaster.setDescription(locationMasterDTO.getDescription());
        locationMaster.setAddress(locationMasterDTO.getAddress());
        locationMaster.setType(locationMasterDTO.getType());
        locationMaster.setCode(locationMasterDTO.getCode());
        locationMaster.setMobileNumber(locationMasterDTO.getMobileNumber());
        locationMaster.setAddDate(new Date());

        return locationMaster;
    }
}
