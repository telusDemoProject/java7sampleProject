package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/create")
    public String createFile(@RequestParam("filename") String filename,
                             @RequestParam("content") String content) {
        return fileService.createFile(filename, content);
    }

    @GetMapping("/read")
    public String readFile(@RequestParam("filename") String filename) {
        return fileService.readFile(filename);
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam("filename") String filename) {
        return fileService.deleteFile(filename);
    }
}
