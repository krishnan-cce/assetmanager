package com.udyata.linentrack.linentrack.controller;

import com.udyata.linentrack.linentrack.dto.*;
import com.udyata.linentrack.linentrack.servicenew.*;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "Bear Authentication")
@RestController
@RequestMapping("/api/item-master")
public class ItemCategoryMasterController {


    private final ItemMasterService itemMasterService;
    private final ItemCategoryMasterService itemCategoryMasterService;



    public ItemCategoryMasterController(ItemMasterService itemMasterService,
                                        ItemCategoryMasterService itemCategoryMasterService

    ) {

        this.itemMasterService = itemMasterService;
        this.itemCategoryMasterService = itemCategoryMasterService;
    }

    /************************************************************************************************************************************
     ***************************************************  LINEN MASTER  *****************************************************************
     ************************************************************************************************************************************/

    @PostMapping("/create-linen-category")
    public ApiResponse<ItemCategoryMasterDTO> createItemCategoryMasterLinen(@RequestBody ItemCategoryMasterDTO itemCategoryMasterDTO) {
        itemCategoryMasterDTO.setItemTypeMasterId(1L);
        ItemCategoryMasterDTO createdItemCategoryMaster = itemCategoryMasterService.createItemCategoryMaster(itemCategoryMasterDTO);
        return new ApiResponse<>(true, HttpStatus.CREATED.value(), createdItemCategoryMaster, "Item Category Master created successfully");
    }

    @GetMapping("/get-linen-category-list")
    public ApiResponse<List<Map<String, Object>>> getItemCategoryMasterLinenList() {
        List<Map<String, Object>> itemCategoryMasterList = itemCategoryMasterService.getItemCategoryMasterList(1);
        return new ApiResponse<>(true, HttpStatus.OK.value(), itemCategoryMasterList, "Item Category Master list fetched successfully");
    }

    @PostMapping("/create-linen-master")
    public ApiResponse<ItemMasterDTO> createLinenMaster(@RequestBody ItemMasterDTO itemMasterDTO) {
        itemMasterDTO.setItemTypeMasterId(1L);
        itemMasterDTO.setItemStatusId(1L);
        ItemMasterDTO createdItemMaster = new ItemMasterDTO();

        if(itemMasterDTO.getRfidTagId() != null){
            createdItemMaster = itemMasterService.createItemMasterLinen(itemMasterDTO);
        }else{
            createdItemMaster = itemMasterService.createItemMaster(itemMasterDTO);
        }

        return new ApiResponse<>(true, HttpStatus.CREATED.value(), createdItemMaster, "Item Master created successfully");
    }

    @GetMapping("/get-linen-list")
    public ApiResponse<List<Map<String, Object>>> getLinenListByRoomID(@RequestParam Long roomId) {
        List<Map<String, Object>> itemCategoryMasterList = itemMasterService.getItemMasterList(1,roomId);
        return new ApiResponse<>(true, HttpStatus.OK.value(), itemCategoryMasterList, "Item Category Master list fetched successfully");
    }


    /************************************************************************************************************************************
     ***********************************************  ItemMaster NEW   ********************************************************************
     ************************************************************************************************************************************/


    @PostMapping("/create-item-master-list")
    public ApiResponse<List<CreateItemMasterDTO>> createMultipleItemMaster(@RequestBody List<CreateItemMasterDTO> itemMasterDTOList) {

        List<CreateItemMasterDTO> createdItemMasterList = itemMasterService.createMultipleItemMaster(itemMasterDTOList);
        return new ApiResponse<>(true, HttpStatus.CREATED.value(), createdItemMasterList, "Item Master's created successfully");
    }

}
