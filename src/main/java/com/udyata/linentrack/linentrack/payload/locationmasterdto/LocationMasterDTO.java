package com.udyata.linentrack.linentrack.payload.locationmasterdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationMasterDTO {
    private Long id;
    private String name;
    private String type;
    private String code;
    private String description;
    private String address;
    private String mobileNumber;
    private String addDate;

}


