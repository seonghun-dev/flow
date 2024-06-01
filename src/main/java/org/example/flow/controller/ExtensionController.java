package org.example.flow.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addExtension(
            @Schema(description = "확장자 이름", example = "pdf")
            @RequestParam String extensionName
    ) {
        var response = extensionService.addExtension(extensionName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteExtension(@PathVariable Long id) {
        extensionService.deleteExtension(id);
    }

    @PatchMapping("/{id}")
    public void toggleFixedExtension(@PathVariable Long id, @RequestParam boolean isOn) {
        extensionService.toggleFixedExtension(id, isOn);
    }


}
