package com.udyata.linentrack.linentrack.serviceimpl.authentication;

import com.udyata.linentrack.linentrack.dto.DocumentDTO;
import com.udyata.linentrack.linentrack.dto.ProfilePhotoDTO;
import com.udyata.linentrack.linentrack.entity.role.Role;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.entitynew.document.Document;
import com.udyata.linentrack.linentrack.entitynew.profilephoto.ProfilePhoto;
import com.udyata.linentrack.linentrack.exception.LinenTrackApiException;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.payload.logindto.LoginDto;
import com.udyata.linentrack.linentrack.payload.registerdto.RegisterDto;
import com.udyata.linentrack.linentrack.repository.role.RoleRepository;
import com.udyata.linentrack.linentrack.repository.user.UserRepository;
import com.udyata.linentrack.linentrack.repositorynnew.DocumentRepository;
import com.udyata.linentrack.linentrack.repositorynnew.ProfilePhotoRepository;
import com.udyata.linentrack.linentrack.security.JwtTokenProvider;
import com.udyata.linentrack.linentrack.service.authentication.AuthService;
import com.udyata.linentrack.linentrack.servicenew.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private FileStorageService fileStorageService;
    private DocumentRepository documentRepository;
    private ProfilePhotoRepository profilePhotoRepository;

    @Value("${file.profile-pic-directory}")
    private String profileDirectory;

    @Value("${file.user-document-directory}")
    private String documentDirectory;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           FileStorageService fileStorageService,
                           DocumentRepository documentRepository,
                           ProfilePhotoRepository profilePhotoRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.fileStorageService=fileStorageService;
        this.documentRepository=documentRepository;
        this.profilePhotoRepository=profilePhotoRepository;
    }

    @Value("${file.base-url}")
    private String baseUrl;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public ProfilePhotoDTO uploadProfilePhoto(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "", userId));

        String filename = fileStorageService.storeFileCpanel(file, profileDirectory);

        ProfilePhoto profilePhoto = new ProfilePhoto();
        profilePhoto.setPhotoUrl(filename);
        profilePhoto.setUser(user);
        profilePhotoRepository.save(profilePhoto);

        String fileUrl = String.format("%s/files/profile-photo/%s", baseUrl, filename);
        return new ProfilePhotoDTO(profilePhoto.getId(), fileUrl);
    }


    @Override
    public DocumentDTO uploadDocument(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "", userId));

        String filename = fileStorageService.storeFileCpanel(file, documentDirectory);

        Document document = new Document();
        document.setDocumentUrl(filename);
        document.setDocumentType(file.getContentType());
        document.setUser(user);
        documentRepository.save(document);

        String fileUrl = String.format("%s/files/document/%s", baseUrl, filename);
        return new DocumentDTO(document.getId(), fileUrl, document.getDocumentType());
    }



    @Override
    public RegisterDto register(RegisterDto registerDto) {

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new LinenTrackApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new LinenTrackApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setAddDate(new Date());
        user.setIsActive(true);

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findById(registerDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "" , registerDto.getRoleId()));


        roles.add(userRole);
        user.setRoles(roles);

        User newUser = userRepository.save(user);

        return mapToDTO(newUser);
    }

    @Override
    @Transactional
    public RegisterDto registerWithFiles(RegisterDto registerDto, MultipartFile profilePicture, List<MultipartFile> documents) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new LinenTrackApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new LinenTrackApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setAddDate(new Date());
        user.setIsActive(true);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findById(registerDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "", registerDto.getRoleId()));
        roles.add(userRole);
        user.setRoles(roles);

        User newUser = userRepository.save(user);

        // Handle profile picture upload if provided
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String filename = fileStorageService.storeFileCpanel(profilePicture, profileDirectory);
            ProfilePhoto profilePhoto = new ProfilePhoto();
            profilePhoto.setPhotoUrl(filename);
            profilePhoto.setUser(newUser);
            profilePhotoRepository.save(profilePhoto);
        }

        // Handle document uploads if provided
        if (documents != null && !documents.isEmpty()) {
            for (MultipartFile document : documents) {
                String filename = fileStorageService.storeFileCpanel(document, documentDirectory);
                Document doc = new Document();
                doc.setDocumentUrl(filename);
                doc.setDocumentType(document.getContentType());
                doc.setUser(newUser);
                documentRepository.save(doc);
            }
        }

        return mapToDTO(newUser);
    }


    @Override
    public List<Role> getUserRoles() {
        return roleRepository.findAll();
    }
    @Override
    public String getProfilePhotoUrl(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        ProfilePhoto profilePhoto = user.getProfilePhoto();
        if (profilePhoto == null) {
            throw new ResourceNotFoundException("ProfilePhoto", "userId", userId);
        }
        return profilePhoto.getPhotoUrl();
    }

    private RegisterDto mapToDTO(User user) {

        RegisterDto registerDto = new RegisterDto();

        registerDto.setUserId(user.getId());
        registerDto.setName(user.getName());
        registerDto.setEmail(user.getEmail());
        registerDto.setName(user.getName());
        registerDto.setUsername(user.getUsername());

        return registerDto;
    }
}