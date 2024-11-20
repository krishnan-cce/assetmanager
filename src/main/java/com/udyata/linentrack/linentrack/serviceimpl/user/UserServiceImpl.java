package com.udyata.linentrack.linentrack.serviceimpl.user;

import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.entitynew.document.Document;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.payload.userdto.UserDTO;
import com.udyata.linentrack.linentrack.repository.user.UserRepository;
import com.udyata.linentrack.linentrack.security.SecurityUtils;
import com.udyata.linentrack.linentrack.service.user.UserService;
import com.udyata.linentrack.linentrack.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final SecurityUtils securityUtils;


    public UserServiceImpl(SecurityUtils securityUtils
    ){
        this.securityUtils = securityUtils;
    }


    @Override
    public UserDTO getUser() {
        User currentUser = securityUtils.getCurrentAuthenticatedUser();
        return mapToDto(currentUser);
    }

    private UserDTO mapToDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setAddDate(DateUtil.formatDateToString(user.getAddDate()));
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPhotoUrl(user.getProfilePhoto().getPhotoUrl());
        userDTO.setDocumentUrl(user.getDocuments().stream().map(Document::getDocumentUrl).toList());
        return userDTO;
    }
}
