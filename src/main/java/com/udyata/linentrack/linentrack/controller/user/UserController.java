package com.udyata.linentrack.linentrack.controller.user;


import com.udyata.linentrack.linentrack.payload.userdto.UserDTO;
import com.udyata.linentrack.linentrack.service.user.UserService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bear Authentication")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){ this.userService = userService ; }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> getUser() {
        UserDTO data = userService.getUser();
        ApiResponse<UserDTO> response = new ApiResponse<>(true, 200, data,"User retrieved successfully.");
        return ResponseEntity.ok(response);
    }

}
