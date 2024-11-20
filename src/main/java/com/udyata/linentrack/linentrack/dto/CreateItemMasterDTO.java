package com.udyata.linentrack.linentrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemMasterDTO {
    private String rfidTag;
    private Long roomMasterId;
    private Long itemStatusId;

}

