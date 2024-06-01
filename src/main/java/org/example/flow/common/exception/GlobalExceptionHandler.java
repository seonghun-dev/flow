package org.example.flow.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ErrorResponseDto handleBusinessException(
            final BusinessException e
    ) {
        return ErrorResponseDto.from(e.getErrorCode());
    }

}
