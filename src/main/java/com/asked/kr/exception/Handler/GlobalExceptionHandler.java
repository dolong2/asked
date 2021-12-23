package com.asked.kr.exception.Handler;

import com.asked.kr.exception.exceptions.EmailDuplicateException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.exception.exceptions.NotSameMemberException;
import com.asked.kr.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(EmailDuplicateException ex){
      log.error("EmailDuplicateException",ex);
      ErrorResponse response=new ErrorResponse(ex.getErrorCode());
      return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(NoMemberException.class)
    public ResponseEntity<ErrorResponse> handleNoMemberException(NoMemberException ex){
        log.error("NoMemberException",ex);
        ErrorResponse response=new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(NotSameMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotSameMemberException(NotSameMemberException ex){
        log.error("NotSameMemberException",ex);
        ErrorResponse response=new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
