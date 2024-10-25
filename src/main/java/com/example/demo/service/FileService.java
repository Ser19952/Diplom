package com.example.demo.service;

import com.example.demo.entity.CloudFile;
import com.example.demo.entity.User;
import com.example.demo.file.StorageProperties;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileService {

    @Value("${storage.location}")
    private String location;


    @PostConstruct
    public void init () throws IOException {
        Files.createDirectories(Paths.get(location));
    }

    public String store (MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()){
                throw new RuntimeException();
            }
            if (fileName.contains("..")){
                throw new RuntimeException();
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(location).resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException( "Не удалось сохранить файл " + fileName, e);
        }

        return fileName;
    }



}



