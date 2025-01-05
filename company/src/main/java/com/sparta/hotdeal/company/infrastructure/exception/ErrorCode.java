package com.sparta.hotdeal.company.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 2000, "예기치 못한 오류가 발생했습니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 2001, "존재하지 않는 리소스입니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, 2002, "올바르지 않은 요청 값입니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, 2003, "권한이 없는 요청입니다."),
    ALREADY_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, 2004, "이미 삭제된 리소스입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, 2005, "인가되지 않는 요청입니다."),
    ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, 2006, "이미 존재하는 리소스입니다."),
    INVALID_SORT_EXCEPTION(HttpStatus.BAD_REQUEST, 2007, "올바르지 않은 정렬 값입니다."),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 3000, "존재하지 않는 사용자입니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
