package org.example.flow.domain.extension.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;


public record ExtensionRequestDto(
        @Schema(description = "확장자 이름", example = "pdf")
        String extensionName
) {

}
