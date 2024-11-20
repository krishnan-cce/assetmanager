package com.udyata.linentrack.linentrack.servicenew;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String directory);
    String storeFileCpanel(MultipartFile file, String directory);
    Resource downloadFile(String directory, String filename);
}
