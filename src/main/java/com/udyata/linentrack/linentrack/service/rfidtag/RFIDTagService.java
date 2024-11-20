package com.udyata.linentrack.linentrack.service.rfidtag;

import com.udyata.linentrack.linentrack.dto.CreateRFIDTagDTO;
import com.udyata.linentrack.linentrack.payload.rfidtagdto.RFIDTagDTO;

import java.util.List;

public interface RFIDTagService {

    CreateRFIDTagDTO createRFIDTagList(CreateRFIDTagDTO rfidTagDTOList);
    List<RFIDTagDTO> getRFIDTagList();

}
