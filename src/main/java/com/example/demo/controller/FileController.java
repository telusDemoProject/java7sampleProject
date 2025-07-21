package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/create")
    public String createFile(@RequestParam("filename") String filename,
                             @RequestParam("content") String content) {
        return Optional.ofNullable(filename)
                .filter(f -> !f.isBlank())
                .flatMap(f -> Optional.ofNullable(content)
                        .filter(c -> !c.isBlank())
                        .map(c -> fileService.createFile(f, c)))
                .orElse("Invalid filename or content");
    }

    @GetMapping("/read")
    public String readFile(@RequestParam("filename") String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> !f.isBlank())
                .map(fileService::readFile)
                .orElse("Invalid filename");
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam("filename") String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> !f.isBlank())
                .map(fileService::deleteFile)
                .orElse("Invalid filename");
    }
}
