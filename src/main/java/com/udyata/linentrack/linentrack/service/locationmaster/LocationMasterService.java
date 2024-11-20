package com.udyata.linentrack.linentrack.service.locationmaster;

import com.udyata.linentrack.linentrack.payload.locationmasterdto.LocationMasterDTO;

import java.util.List;

public interface LocationMasterService {
    LocationMasterDTO createLocationMaster(LocationMasterDTO locationMasterDTO);
    LocationMasterDTO updateLocationMaster(LocationMasterDTO locationMasterDTO);
    List<LocationMasterDTO> getLocationMasterList();
}
