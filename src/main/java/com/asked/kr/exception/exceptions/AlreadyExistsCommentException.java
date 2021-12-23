package com.asked.kr.exception.exceptions;

import com.asked.kr.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyExistsCommentException extends RuntimeException{
    private final ErrorCode errorCode;

    public AlreadyExistsCommentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
