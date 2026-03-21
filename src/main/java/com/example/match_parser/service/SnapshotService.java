package com.example.match_parser.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SnapshotService {

    private static final String STORAGE_DIR = "import-storage";

    public String saveHtml(Long importId, String html) throws IOException {
        Path dir = Path.of(STORAGE_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String fileName = "import_" + importId + ".html";
        Path filePath = dir.resolve(fileName);

        Files.writeString(filePath, html);

        return filePath.toString();
    }

    public String readHtml(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
