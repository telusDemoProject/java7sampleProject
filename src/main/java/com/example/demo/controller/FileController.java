package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFile(@RequestParam("filename") String filename,
                                           @RequestParam("content") String content) {
        return ResponseEntity.ok(fileService.createFile(filename, content));
    }

    @GetMapping("/read")
    public ResponseEntity<String> readFile(@RequestParam("filename") String filename) {
        return ResponseEntity.ok(fileService.readFile(filename));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        return ResponseEntity.ok(fileService.deleteFile(filename));
    }
}
