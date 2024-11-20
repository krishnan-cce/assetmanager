package com.udyata.linentrack.linentrack.service.authentication;

import com.udyata.linentrack.linentrack.dto.DocumentDTO;
import com.udyata.linentrack.linentrack.dto.ProfilePhotoDTO;
import com.udyata.linentrack.linentrack.entity.role.Role;
import com.udyata.linentrack.linentrack.entitynew.document.Document;
import com.udyata.linentrack.linentrack.entitynew.profilephoto.ProfilePhoto;
import com.udyata.linentrack.linentrack.payload.logindto.LoginDto;
import com.udyata.linentrack.linentrack.payload.registerdto.RegisterDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthService {
    String login(LoginDto loginDto);
    ProfilePhotoDTO uploadProfilePhoto(Long userId, MultipartFile file);
    DocumentDTO uploadDocument(Long userId, MultipartFile file);

    RegisterDto register(RegisterDto registerDto);
    RegisterDto registerWithFiles(RegisterDto registerDto, MultipartFile profilePicture, List<MultipartFile> documents);
    List<Role> getUserRoles();

    String getProfilePhotoUrl(Long userId);

}