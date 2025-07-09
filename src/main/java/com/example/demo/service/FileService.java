package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    private static final String BASE_PATH = "files/";

    public FileService() {
        File folder = new File(BASE_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String createFile(String filename, String content) {
        try {
            File file = new File(BASE_PATH + filename);
            if (file.exists()) {
                return "File already exists.";
            }
            // Java 8 try-with-resources
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
            return "File created successfully.";
        } catch (IOException e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    public String readFile(String filename) {
        try {
            File file = new File(BASE_PATH + filename);
            if (!file.exists()) {
                return "File not found.";
            }
            // Java 8 try-with-resources and streams
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.lines().collect(java.util.stream.Collectors.joining("\n"));
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        File file = new File(BASE_PATH + filename);
        if (!file.exists()) {
            return "File not found.";
        }
        if (file.delete()) {
            return "File deleted successfully.";
        } else {
            return "Error deleting file.";
        }
    }
}
