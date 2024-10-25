package com.example.demo.repository;

import com.example.demo.entity.CloudFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<CloudFile,Long> {
}
