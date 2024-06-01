package org.example.flow.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    EXTENSION_FIXED_EXTENSION_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "EXTENSION_FIXED_EXTENSION_CANNOT_BE_DELETED", "고정 확장자 삭제 불가", "고정 확장자는 삭제할 수 없습니다."),
    EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "EXTENSION_NOT_FOUND", "확장자를 찾을 수 없음", "해당 확장자를 찾을 수 없습니다."),
    EXTENSION_ALREADY_EXISTS(HttpStatus.CONFLICT, "EXTENSION_ALREADY_EXISTS", "이미 존재하는 확장자", "이미 존재하는 확장자입니다."),
    EXTENSION_INVALID_LENGTH_INPUT(HttpStatus.BAD_REQUEST, " EXTENSION_INVALID_LENGTH_INPUT", "확장자 길이 오류", "확장자는 1자 이상 20자 이하로 입력해주세요.");


    private final HttpStatus httpStatus;
    private final String errorResponseCode;
    private final String title;
    private final String message;

}
