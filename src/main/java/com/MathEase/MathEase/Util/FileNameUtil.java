package com.MathEase.MathEase.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileNameUtil {

    public final String UPLOAD_DIR = "src/main/resources/static/data/";

    public String transferFile(MultipartFile sourceFile, String targetDirectory) {
        if (sourceFile == null || sourceFile.isEmpty()) {
            return null; // Handle case where sourceFile is null or empty
        }

        String originalFileName = sourceFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null; // Handle case where original filename is missing
        }

        String uniqueFileName = generateUniqueFileName(originalFileName);

        try {
            Path targetPath = Paths.get(targetDirectory, uniqueFileName);
            Files.createDirectories(targetPath.getParent());
            Files.copy(sourceFile.getInputStream(), targetPath);
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle file transfer failure
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueName = UUID.randomUUID().toString() + extension;
        return uniqueName;
    }
}
