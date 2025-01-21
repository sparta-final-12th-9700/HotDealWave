package com.sparta.hotdeal.payment.application.dtos;

import com.sparta.hotdeal.payment.common.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private String code;
    private String message;

    public ErrorResponseDto(ErrorCode errorcode) {
        this(LocalDateTime.now(), errorcode.getCode(), errorcode.getMessage());
    }

    public ErrorResponseDto(String message) {
        this(LocalDateTime.now(), ErrorCode.INTERNAL_SERVER_EXCEPTION.getCode(), message);
    }

    public ErrorResponseDto(ErrorCode errorcode, String message) {
        this(LocalDateTime.now(), errorcode.getCode(), message);
    }
}
