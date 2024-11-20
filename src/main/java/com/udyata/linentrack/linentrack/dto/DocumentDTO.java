package com.udyata.linentrack.linentrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private String documentUrl;
    private String documentType;
}
