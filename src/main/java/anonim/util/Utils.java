package anonim.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Slf4j
public class Utils {
    public static Boolean isNumeric(String number) {
        try {
            Long.parseLong(number);
            Integer.parseInt(number);
            Double.parseDouble(number);
            Float.parseFloat(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validPhone(String phone) {
        try {
            Long.parseLong(phone);
            return validUzbPhone(phone);
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateIdentifier() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(24);
    }

    private static boolean validUzbPhone(String phone) {
        if (phone.startsWith("+")) phone = phone.substring(1);
        return phone.length() == 12 && phone.startsWith("998");
    }

    public static String downloadFile(String urlParam, String filename) {
        final String path = "/var/anonim/files/";
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException ignored) {
            }
        }

        try {
            URL url = new URL(urlParam);
            URLConnection connection = url.openConnection();
            String filePath = generateIdentifier() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd-MM-yyy_hh mm_")) + filename.split("/")[1];

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(
                     path + filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return path + filePath;
            }
        } catch (Exception e) {
            log.error("Unable to download file from telegram server {}", e.getMessage());
        }
        return "unknown";
    }
}
