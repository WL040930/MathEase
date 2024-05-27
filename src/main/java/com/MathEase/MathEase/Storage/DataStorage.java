package com.MathEase.MathEase.Storage;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class DataStorage {

    public static final String UPLOAD_DIRECTORY = "src/main/resources/static/data/";

    public static String saveFile(MultipartFile file, String directory) throws IOException {
        // Get the file bytes
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename(); // Get the original file name
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + fileExtension;
        Path path = Paths.get(directory + newFilename);
        Files.write(path, bytes);
        return newFilename;
    }


}
