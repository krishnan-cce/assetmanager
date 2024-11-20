package com.udyata.linentrack.linentrack.controller.authentication;


import com.udyata.linentrack.linentrack.dto.DocumentDTO;
import com.udyata.linentrack.linentrack.dto.ProfilePhotoDTO;
import com.udyata.linentrack.linentrack.entity.role.Role;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.payload.jwtauthresponse.JWTAuthResponse;
import com.udyata.linentrack.linentrack.payload.logindto.LoginDto;
import com.udyata.linentrack.linentrack.payload.registerdto.RegisterDto;
import com.udyata.linentrack.linentrack.repository.user.UserRepository;
import com.udyata.linentrack.linentrack.service.authentication.AuthService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository=userRepository;
    }

    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", loginDto.getUsernameOrEmail(), 0));

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setUserId(user.getId());

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Hidden
    @PostMapping(value = {"/register"})
    public ResponseEntity<ApiResponse<RegisterDto>> register(@RequestBody RegisterDto registerDto){
        RegisterDto data = authService.register(registerDto);
        String message = "User Registered Successfully !";
        ApiResponse<RegisterDto> response = new ApiResponse<>(true, 200, data, message);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register-with-files"})
    public ResponseEntity<ApiResponse<RegisterDto>> registerWithFiles(
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roleId") Long roleId,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestParam(value = "documents", required = false) List<MultipartFile> documents) {

        RegisterDto registerDto = new RegisterDto();
        registerDto.setName(name);
        registerDto.setUsername(username);
        registerDto.setEmail(email);
        registerDto.setPassword(password);
        registerDto.setRoleId(roleId);

        RegisterDto data = authService.registerWithFiles(registerDto, profilePicture, documents);
        String message = "User Registered Successfully with Files!";
        ApiResponse<RegisterDto> response = new ApiResponse<>(true, 200, data, message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-roles")
    public ResponseEntity<ApiResponse<List<Role>>> getUserRoles() {
        List<Role> result = authService.getUserRoles();
        ApiResponse<List<Role>> response = new ApiResponse<>(true, HttpStatus.OK.value(), result, "Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/{userId}/profile-photo")
    public ResponseEntity<ApiResponse<ProfilePhotoDTO>> uploadProfilePhoto(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        ProfilePhotoDTO profilePhoto = authService.uploadProfilePhoto(userId, file);
        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.OK.value(), profilePhoto, "Profile photo uploaded successfully"), HttpStatus.OK);
    }

    @PostMapping("/{userId}/documents")
    public ResponseEntity<ApiResponse<DocumentDTO>> uploadDocument(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        DocumentDTO document = authService.uploadDocument(userId, file);
        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.OK.value(), document, "Document uploaded successfully"), HttpStatus.OK);
    }
}