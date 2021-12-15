package com.asked.kr.exception.exceptions;

import com.asked.kr.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException{
    private ErrorCode errorCode;

    public EmailDuplicateException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
