package org.example.flow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExtensionToggleRequestDto(
        @Schema(description = "확장자 사용여부", example = "true")
        boolean isOn
) {

}
