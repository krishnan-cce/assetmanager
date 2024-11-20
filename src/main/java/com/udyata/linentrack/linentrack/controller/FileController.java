package com.udyata.linentrack.linentrack.controller;

import com.udyata.linentrack.linentrack.dto.ProfilePhotoDTO;
import com.udyata.linentrack.linentrack.service.authentication.AuthService;
import com.udyata.linentrack.linentrack.servicenew.FileStorageService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;

@SecurityRequirement(name = "Bear Authentication")
@RestController
@RequestMapping("/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.profile-pic-directory}")
    private String profileDirectory;

    @Value("${file.user-document-directory}")
    private String documentDirectory;

    private final FileStorageService sftpService;

    public FileController(FileStorageService sftpService) {
        this.sftpService = sftpService;
    }


    @GetMapping("/profile-photo/{filename:.+}")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable String filename) {
        try {
            Resource file = sftpService.downloadFile(profileDirectory, filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
        } catch (Exception e) {
            logger.error("Error serving profile photo: {}", filename, e);
            throw e;
        }
    }

    @GetMapping("/document/{filename:.+}")
    public ResponseEntity<Resource> getDocument(@PathVariable String filename) {
        try {
            Resource file = sftpService.downloadFile(documentDirectory, filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        } catch (Exception e) {
            logger.error("Error serving document: {}", filename, e);
            throw e;
        }
    }
}