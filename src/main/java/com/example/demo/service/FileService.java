package com.example.demo.service;

import com.example.demo.entity.CloudFile;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserService userService;

    public FileService(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    public void uploadFile(String filename,
                                 MultipartFile file) throws IOException {
        uploadFile(filename, file,null);
    }


    public Optional<CloudFile> getFileByName(String fileName) {
        Optional<User> userOptional = userService.findByLogin(getAuthenticationLogin());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Пользователя такого нет");
        }
        return fileRepository.findByNameAndUser(fileName, userOptional.get());

    }

    public boolean deleteFile(String fileName) {
        fileRepository.deleteByName(fileName);
        return false;
    }

    public void uploadFile(String filename,
                                 MultipartFile file, CloudFile uploadFile) throws IOException {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            if (uploadFile==null) {
                uploadFile = new CloudFile();
            }
            uploadFile.setName(filename);
            uploadFile.setData(file.getBytes());
            uploadFile.setSize(file.getSize());
            uploadFile.setUser(optionalUser.get());

            fileRepository.saveAndFlush(uploadFile);
        } else {
            throw new UserNotFoundException("Пользователя такого нет");
        }
    }

     public List<CloudFile> showSavedFile(Integer limit) {

         String name = getAuthenticationLogin();
        Optional<User> optionalUser = userService.findByLogin(name);
         return limit==null? fileRepository.findByUser(optionalUser.get()): fileRepository.findAllByUserIdWithLimit(optionalUser.get().getId(),limit);
     }

    public String getAuthenticationLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}









