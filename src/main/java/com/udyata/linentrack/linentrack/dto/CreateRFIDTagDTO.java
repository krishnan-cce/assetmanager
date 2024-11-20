package com.udyata.linentrack.linentrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRFIDTagDTO {
    private List<String> tagCode;
    private Long itemCategoryMasterId;
    private Long itemTypeMasterId;
}
