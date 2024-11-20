package com.udyata.linentrack.linentrack.serviceimpl.rfidtag;

import com.udyata.linentrack.linentrack.dto.CreateRFIDTagDTO;
import com.udyata.linentrack.linentrack.entity.rfidtag.RFIDTag;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.payload.rfidtagdto.RFIDTagDTO;
import com.udyata.linentrack.linentrack.repository.rfidtag.RFIDTagRepository;
import com.udyata.linentrack.linentrack.repositorynnew.ItemCategoryMasterRepository;
import com.udyata.linentrack.linentrack.repositorynnew.ItemTypeMasterRepository;
import com.udyata.linentrack.linentrack.security.SecurityUtils;
import com.udyata.linentrack.linentrack.service.log.LogService;
import com.udyata.linentrack.linentrack.service.rfidtag.RFIDTagService;
import com.udyata.linentrack.linentrack.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RFIDTagServiceImpl implements RFIDTagService {

    private final SecurityUtils securityUtils;
    private final RFIDTagRepository rfidTagRepository;
    private final ItemTypeMasterRepository itemTypeMasterRepository;
    private final ItemCategoryMasterRepository itemCategoryMasterRepository;
    private final LogService logService;

    public RFIDTagServiceImpl(SecurityUtils securityUtils,
                              RFIDTagRepository rfidTagRepository, ItemTypeMasterRepository itemTypeMasterRepository, ItemCategoryMasterRepository itemCategoryMasterRepository, LogService logService
    ){
        this.securityUtils=securityUtils;
        this.rfidTagRepository=rfidTagRepository;
        this.itemTypeMasterRepository = itemTypeMasterRepository;
        this.itemCategoryMasterRepository = itemCategoryMasterRepository;
        this.logService = logService;
    }

    @Override
    @Transactional
    public CreateRFIDTagDTO createRFIDTagList(CreateRFIDTagDTO rfidTagDTOList) {

        User currentUser = securityUtils.getCurrentAuthenticatedUser();
        assert currentUser != null;

        ItemTypeMaster itemTypeMaster = itemTypeMasterRepository.findById(rfidTagDTOList.getItemTypeMasterId())
                .orElseThrow(() -> new ResourceNotFoundException("ItemTypeMaster", "" , rfidTagDTOList.getItemTypeMasterId()));


        ItemCategoryMaster itemCategoryMaster = itemCategoryMasterRepository.findById(rfidTagDTOList.getItemCategoryMasterId())
                .orElseThrow(() -> new ResourceNotFoundException("ItemCategoryMaster", "" , rfidTagDTOList.getItemCategoryMasterId()));

        List<RFIDTag> RFIDTagList = new ArrayList<>();

        for (String tag : rfidTagDTOList.getTagCode()){
            RFIDTag rfidTag = new RFIDTag();
            rfidTag.setAddDate(new Date());
            rfidTag.setItemTypeMaster(itemTypeMaster);
            rfidTag.setItemCategoryMaster(itemCategoryMaster);
            rfidTag.setAddedBy(currentUser.getId());
            rfidTag.setTagCode(tag);

            RFIDTagList.add(rfidTag);
        }

        List<RFIDTag> savedRfidTags = rfidTagRepository.saveAll(RFIDTagList);

        CreateRFIDTagDTO createdTagsDTO = new CreateRFIDTagDTO();
        createdTagsDTO.setTagCode(savedRfidTags.stream().map(RFIDTag::getTagCode).collect(Collectors.toList()));
        createdTagsDTO.setItemTypeMasterId(rfidTagDTOList.getItemTypeMasterId());
        createdTagsDTO.setItemCategoryMasterId(rfidTagDTOList.getItemCategoryMasterId());

        return createdTagsDTO;
    }


    @Override
    public List<RFIDTagDTO> getRFIDTagList() {
        List<RFIDTag> rfidTagList = rfidTagRepository.findRFIDTagsNotAssignedToItemMaster();
        return rfidTagList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private RFIDTagDTO mapToDTO(RFIDTag rfidTag) {
        RFIDTagDTO rfidTagDTO = new RFIDTagDTO();
        rfidTagDTO.setId(rfidTag.getId());
        rfidTagDTO.setTagCode(rfidTag.getTagCode());
        rfidTagDTO.setCategoryName(rfidTag.getItemCategoryMaster().getName());
        rfidTagDTO.setItemType(rfidTag.getItemTypeMaster().getName());
        rfidTagDTO.setAddDate(DateUtil.formatDateToString(rfidTag.getAddDate()));
        return rfidTagDTO;
    }
    private RFIDTag mapToEntity(RFIDTagDTO rfidTagDTO) {
        User currentUser = securityUtils.getCurrentAuthenticatedUser();
        RFIDTag rfidTag = new RFIDTag();
        rfidTag.setTagCode(rfidTagDTO.getTagCode());
        rfidTag.setAddDate(new Date());
        if (currentUser != null) {
            rfidTag.setAddedBy(currentUser.getId());
        }
        return rfidTag;
    }

}
