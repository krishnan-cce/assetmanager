package com.udyata.linentrack.linentrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemMasterDTO {
    private Long id;
    private String name;
    private String description;
    private String addDate;
    private Long itemStatusId;
    private String rfidTag;
    private Long rfidTagId;
    private Long roomMasterId;
    private Long itemCategoryMasterId;
    private Long itemTypeMasterId;
}

