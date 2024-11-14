package com.example.demo.controllers;

import com.example.demo.entity.CloudFile;
import com.example.demo.exceptions.FileStorageException;
import com.example.demo.service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/list")
    public List<CloudFile> getFileList (@RequestParam ("limit") Integer limit) {
        return fileService.showSavedFile(limit);
    }

    @PostMapping ("/file")
    public ResponseEntity<Void> saveFile (String fileName, MultipartFile file) throws Exception{
        fileService.uploadFile(fileName , file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestParam ("fileName") String fileName ) throws Exception{
        fileService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/file")
    public byte[] getFile (@RequestParam ("fileName") String fileName) {
       return fileService.getFileByName(fileName).orElseThrow(() -> new FileStorageException("Файл не найден")).getData();
    }

    @PutMapping("/file")
    public ResponseEntity<Void> editFile(@RequestParam("filename") String fileName,MultipartFile file) throws IOException {
       CloudFile cloudFile = fileService.getFileByName(fileName).orElseThrow(() -> new RuntimeException("Файл не найден"));
       fileService.uploadFile(fileName, file,cloudFile);
        return ResponseEntity.ok().build();
    }
}




