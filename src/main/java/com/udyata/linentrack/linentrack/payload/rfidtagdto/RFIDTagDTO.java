package com.udyata.linentrack.linentrack.payload.rfidtagdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RFIDTagDTO {
    private Long id;
    private String tagCode;
    private String categoryName;
    private String itemType;
    private String addDate;
}
