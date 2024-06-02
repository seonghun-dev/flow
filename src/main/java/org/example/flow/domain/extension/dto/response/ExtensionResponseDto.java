package org.example.flow.domain.extension.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.flow.domain.extension.entity.Extension;

public record ExtensionResponseDto(
        @Schema(description = "확장자 ID", example = "1")
        Long id,
        @Schema(description = "확장자명", example = "Google")
        String name,
        @Schema(description = "확장자 활성화 여부", example = "true")
        boolean isOn
) {
    public static ExtensionResponseDto from(Extension extension) {
        return new ExtensionResponseDto(extension.getId(), extension.getName(), extension.isOn());
    }

}


