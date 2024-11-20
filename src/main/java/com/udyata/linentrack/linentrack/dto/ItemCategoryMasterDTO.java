package com.udyata.linentrack.linentrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryMasterDTO {
    private Long id;
    private String name;
    private Long itemTypeMasterId;
}

