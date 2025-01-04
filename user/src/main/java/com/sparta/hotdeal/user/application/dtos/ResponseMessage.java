package com.sparta.hotdeal.user.application.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    SIGNUP_SUCCESS("회원가입 성공"),
    USABLE_EMAIL("사용할 수 있는 이메일입니다."),
    EMAIL_SENT("이메일 인증이 요청되었습니다. 메일을 확인해주세요.");

    private final String message;
}
