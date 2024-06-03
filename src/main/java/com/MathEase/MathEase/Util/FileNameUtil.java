package com.MathEase.MathEase.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileNameUtil {

    public final String UPLOAD_DIR = "src/main/resources/static/data/";

    // transfer file
    public String transferFile(MultipartFile sourceFile, String targetDirectory) {
        if (sourceFile == null || sourceFile.isEmpty()) {
            return null;
        }

        // get the original file name
        String originalFileName = sourceFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null;
        }

        // generate a unique file name
        String uniqueFileName = generateUniqueFileName(originalFileName);

        try {
            // create the target path
            Path targetPath = Paths.get(targetDirectory, uniqueFileName);
            Files.createDirectories(targetPath.getParent());
            // copy the file to the target path
            Files.copy(sourceFile.getInputStream(), targetPath);
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // generate a unique file name
    public String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueName = UUID.randomUUID().toString() + extension;
        return uniqueName;
    }

    // get the current date
    public String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}
