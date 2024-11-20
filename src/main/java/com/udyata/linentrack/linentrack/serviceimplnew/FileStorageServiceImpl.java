package com.udyata.linentrack.linentrack.serviceimplnew;

import com.udyata.linentrack.linentrack.servicenew.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Properties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Value("${file.profile-pic-directory}")
    private String profileDirectory;

    @Value("${file.user-document-directory}")
    private String documentDirectory;

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port}")
    private int sftpPort;

    @Value("${sftp.user}")
    private String sftpUser;

    @Value("${sftp.password}")
    private String sftpPassword;


    public Resource downloadFile(String directory, String filename) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            String remoteFilePath = directory + filename;
            logger.info("Downloading file: {}", remoteFilePath);

            InputStream inputStream = channelSftp.get(remoteFilePath);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            logger.info("Successfully downloaded file: {}", remoteFilePath);
            return new ByteArrayResource(outputStream.toByteArray());

        } catch (Exception e) {
            logger.error("Failed to download file via SFTP", e);
            throw new RuntimeException("Failed to download file via SFTP", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    @Override
    public String storeFile(MultipartFile file, String directory) {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = directory + filename;

        try {
            Files.createDirectories(Paths.get(directory));
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Failed to store file locally", e);
            throw new RuntimeException("Failed to store file locally", e);
        }

        return filePath;
    }

    @Override
    public String storeFileCpanel(MultipartFile file, String directory) {

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String remoteFilePath = directory + filename;

        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            InputStream inputStream = file.getInputStream();
            channelSftp.put(inputStream, remoteFilePath);
            inputStream.close();

        } catch (Exception e) {
            logger.error("Failed to store file via SFTP", e);
            throw new RuntimeException("Failed to store file via SFTP", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

        return filename;
    }


}