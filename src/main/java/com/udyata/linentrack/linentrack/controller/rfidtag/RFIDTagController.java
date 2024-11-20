package com.udyata.linentrack.linentrack.controller.rfidtag;

import com.udyata.linentrack.linentrack.dto.CreateRFIDTagDTO;
import com.udyata.linentrack.linentrack.payload.rfidtagdto.RFIDTagDTO;
import com.udyata.linentrack.linentrack.service.rfidtag.RFIDTagService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bear Authentication")
@RestController
@RequestMapping("/api/rfid-tags")
public class RFIDTagController {

    private final RFIDTagService rfidTagService;

    public RFIDTagController(RFIDTagService rfidTagService) {
        this.rfidTagService = rfidTagService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateRFIDTagDTO>> createRFIDTag(@RequestBody CreateRFIDTagDTO rfidTagDTO) {
        CreateRFIDTagDTO createdRFIDTag = rfidTagService.createRFIDTagList(rfidTagDTO);
        ApiResponse<CreateRFIDTagDTO> response = new ApiResponse<>(true, HttpStatus.CREATED.value(), createdRFIDTag, "RFID tag created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RFIDTagDTO>>> getRFIDTagList() {
        List<RFIDTagDTO> rfidTagList = rfidTagService.getRFIDTagList();
        ApiResponse<List<RFIDTagDTO>> response = new ApiResponse<>(true, HttpStatus.OK.value(), rfidTagList, "RFID tag list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
