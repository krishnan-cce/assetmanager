package com.udyata.linentrack.linentrack.servicenew;

import com.udyata.linentrack.linentrack.dto.ItemCategoryMasterDTO;

import java.util.List;
import java.util.Map;

public interface ItemCategoryMasterService {
    ItemCategoryMasterDTO createItemCategoryMaster(ItemCategoryMasterDTO itemCategoryMasterDTO);
    List<Map<String, Object>> getItemCategoryMasterList(Integer itemTypeId);
}
