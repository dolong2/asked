package com.asked.kr.exception.exceptions;

import com.asked.kr.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
