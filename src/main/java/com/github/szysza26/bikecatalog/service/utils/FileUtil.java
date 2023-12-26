package com.github.szysza26.bikecatalog.service.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtil {
    public static String generateUniqueFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        String uniqueId = UUID.randomUUID().toString().substring(0, 8);

        return formattedDateTime + "_" + uniqueId + "." + fileExtension;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }

    public static boolean saveFile(Path path, MultipartFile file) {
        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
