package org.example.flow.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.example.flow.common.annotaion.ValidFileExtension;
import org.example.flow.common.dto.ResponseDto;
import org.example.flow.domain.file.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> uploadFile(
            @RequestPart(value = "file")
            @ValidFileExtension
            MultipartFile file
    ) {
        fileService.uploadFile(file);
        return ResponseDto.noContent();
    }
}
