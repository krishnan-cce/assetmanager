package com.udyata.linentrack.linentrack.payload.userdto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String addDate;
    private String photoUrl;
    private List<String> documentUrl;
    private Long userId;
}
