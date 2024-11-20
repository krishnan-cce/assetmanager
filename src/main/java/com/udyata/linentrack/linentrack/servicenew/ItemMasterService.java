package com.udyata.linentrack.linentrack.servicenew;

import com.udyata.linentrack.linentrack.dto.CreateItemMasterDTO;
import com.udyata.linentrack.linentrack.dto.ItemCategoryMasterDTO;
import com.udyata.linentrack.linentrack.dto.ItemMasterDTO;

import java.util.List;
import java.util.Map;

public interface ItemMasterService {

    List<CreateItemMasterDTO> createMultipleItemMaster(List<CreateItemMasterDTO> itemMasterDTOList);
    ItemMasterDTO createItemMaster(ItemMasterDTO itemMasterDTO);
    ItemMasterDTO createItemMasterLinen(ItemMasterDTO itemMasterDTO);
    List<Map<String, Object>> getItemMasterList(Integer itemTypeId,Long roomId);
    List<Map<String, Object>> getDashBoardData();
}
