package org.example.flow.domain.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(MultipartFile file);
}
