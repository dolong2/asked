package com.asked.kr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 에러코드와 메시지는 무조건 대문자로 작성
     * */
    UNKNOWN_ERROR(500, "SERVER-ERR-500", "Unknown Error"),
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    EMAIL_DUPLICATION(400,"MEMBER-ERR-400","EMAIL DUPLICATED"),
    NO_MEMBER(404,"MEMBER-ERR-404","CAN'T FIND MEMBER"),
    NO_AUTHORIZATION(401,"MEMBER-ERR-401","NO AUTHORIZATION TO ACCESS"),

    ;
    private int status;
    private String errorCode;
    private String message;
}
