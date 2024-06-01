package org.example.flow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record ExtensionListResponseDto(
        @Schema(description = "고정 확장자 목록")
        List<ExtensionResponseDto> fixedExtensionList,
        @Schema(description = "사용자 확장자 목록")
        List<ExtensionResponseDto> customExtensionList) {
}
