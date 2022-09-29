package com.asked.kr.exception.exceptions;

import com.asked.kr.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotSameReceiverException extends RuntimeException{
    private ErrorCode errorCode;

    public NotSameReceiverException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
