package org.example.flow.controller;

import lombok.RequiredArgsConstructor;
import org.example.flow.common.dto.ResponseDto;
import org.example.flow.dto.request.ExtensionRequestDto;
import org.example.flow.dto.request.ExtensionToggleRequestDto;
import org.example.flow.dto.response.ExtensionListResponseDto;
import org.example.flow.service.ExtensionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    @GetMapping
    public ResponseEntity<ExtensionListResponseDto> getAllExtensions() {
        var response = extensionService.getAllExtensions();
        return ResponseDto.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addExtension(@RequestBody ExtensionRequestDto extensionRequestDto) {
        extensionService.addExtension(extensionRequestDto.extensionName());
        return ResponseDto.createdWithStatus();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtension(@PathVariable Long id) {
        extensionService.deleteExtension(id);
        return ResponseDto.noContent();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> toggleFixedExtension(
            @PathVariable Long id,
            @RequestBody ExtensionToggleRequestDto extensionToggleRequestDto
    ) {
        extensionService.toggleFixedExtension(id, extensionToggleRequestDto.isOn());
        return ResponseDto.noContent();
    }

}
