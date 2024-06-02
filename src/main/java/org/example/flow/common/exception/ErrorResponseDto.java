package org.example.flow.common.exception;

public record ErrorResponseDto(
        String errorResponseCode,
        String title,
        String message
) {
    public static ErrorResponseDto from(ErrorCode errorCode) {
        return new ErrorResponseDto(errorCode.getErrorResponseCode(), errorCode.getTitle(), errorCode.getMessage());
    }

}
