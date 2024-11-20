package com.udyata.linentrack.linentrack.controller.locationmaster;

import com.udyata.linentrack.linentrack.payload.locationmasterdto.LocationMasterDTO;
import com.udyata.linentrack.linentrack.service.locationmaster.LocationMasterService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bear Authentication")
@RestController
@RequestMapping("/api/locationMaster")
public class LocationMasterController {

    private final LocationMasterService locationMasterService;

    public LocationMasterController(LocationMasterService locationMasterService) {
        this.locationMasterService = locationMasterService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LocationMasterDTO>> createLocationMaster(@RequestBody LocationMasterDTO locationMasterDTO) {
        LocationMasterDTO createdLocationMaster = locationMasterService.createLocationMaster(locationMasterDTO);
        ApiResponse<LocationMasterDTO> response = new ApiResponse<>(true, HttpStatus.CREATED.value(), createdLocationMaster, "LocationMaster Created Successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<LocationMasterDTO>> updateLocationMaster(@RequestBody LocationMasterDTO locationMasterDTO) {
        LocationMasterDTO updatedLocationMaster = locationMasterService.updateLocationMaster(locationMasterDTO);
        ApiResponse<LocationMasterDTO> response = new ApiResponse<>(true, HttpStatus.OK.value(), updatedLocationMaster, "LocationMaster Updated Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationMasterDTO>>> getLocationMasterList() {
        List<LocationMasterDTO> locationMasters = locationMasterService.getLocationMasterList();
        ApiResponse<List<LocationMasterDTO>> response = new ApiResponse<>(true, HttpStatus.OK.value(), locationMasters, "LocationMaster List Retrieved Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}