package com.MathEase.MathEase.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileNameUtil {

    public final String UPLOAD_DIR = "src/main/resources/static/data/";

    public static String generateFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;
        return fileName;
    }

    public void transferFile(MultipartFile sourceFile, String fileName, String targetDirectory) {
        Path targetPath = Paths.get(targetDirectory, fileName);

        try {
            Files.createDirectories(targetPath.getParent());
            sourceFile.transferTo(targetPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
