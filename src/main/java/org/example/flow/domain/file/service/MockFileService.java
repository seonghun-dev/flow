package org.example.flow.domain.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MockFileService implements FileService {
    @Override
    public void uploadFile(MultipartFile file) {
    }
}
