package com.udyata.linentrack.linentrack.serviceimplnew;

import com.udyata.linentrack.linentrack.dto.ItemCategoryMasterDTO;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.repositorynnew.ItemCategoryMasterRepository;
import com.udyata.linentrack.linentrack.repositorynnew.ItemTypeMasterRepository;
import com.udyata.linentrack.linentrack.security.SecurityUtils;
import com.udyata.linentrack.linentrack.servicenew.ItemCategoryMasterService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemCategoryMasterServiceImpl implements ItemCategoryMasterService {

    private final SecurityUtils securityUtils;
    private final ItemCategoryMasterRepository itemCategoryMasterRepository;
    private final ItemTypeMasterRepository itemTypeMasterRepository;

    public ItemCategoryMasterServiceImpl(SecurityUtils securityUtils, ItemCategoryMasterRepository itemCategoryMasterRepository,
                                         ItemTypeMasterRepository itemTypeMasterRepository
    ) {
        this.securityUtils = securityUtils;
        this.itemCategoryMasterRepository = itemCategoryMasterRepository;
        this.itemTypeMasterRepository = itemTypeMasterRepository;
    }

    @Override
    public ItemCategoryMasterDTO createItemCategoryMaster(ItemCategoryMasterDTO itemCategoryMasterDTO) {
        User currentUser = securityUtils.getCurrentAuthenticatedUser();
        assert currentUser != null;

        ItemTypeMaster itemTypeMaster = itemTypeMasterRepository.findById(itemCategoryMasterDTO.getItemTypeMasterId())
                .orElseThrow(() -> new ResourceNotFoundException("ItemTypeMaster", "", itemCategoryMasterDTO.getItemTypeMasterId()));

        ItemCategoryMaster itemCategoryMaster = new ItemCategoryMaster();
        itemCategoryMaster.setAddedBy(currentUser.getId());
        itemCategoryMaster.setName(itemCategoryMasterDTO.getName());
        itemCategoryMaster.setItemTypeMaster(itemTypeMaster);
        itemCategoryMaster.setAddDate(new Date());
        ItemCategoryMaster newItemCategoryMaster = itemCategoryMasterRepository.save(itemCategoryMaster);

        return mapToDTO(newItemCategoryMaster);
    }

    @Override
    public List<Map<String, Object>> getItemCategoryMasterList(Integer itemTypeId) {
        return itemCategoryMasterRepository.getItemCategoryMaster(itemTypeId);
    }

    private ItemCategoryMasterDTO mapToDTO(ItemCategoryMaster itemCategoryMaster) {

        ItemCategoryMasterDTO itemCategoryMasterDTO = new ItemCategoryMasterDTO();

        itemCategoryMasterDTO.setId(itemCategoryMaster.getId());
        itemCategoryMasterDTO.setItemTypeMasterId(itemCategoryMaster.getItemTypeMaster().getId());
        itemCategoryMasterDTO.setName(itemCategoryMaster.getName());


        return itemCategoryMasterDTO;
    }
}
