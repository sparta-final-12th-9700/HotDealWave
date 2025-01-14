package com.sparta.hotdeal.product.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "P001", "예기치 못한 오류가 발생했습니다."),
    PRODUCT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "P002", "존재하지 않는 상품입니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, "P003", "올바르지 않은 요청 값입니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "P004", "권한이 없는 요청입니다."),
    PRODUCT_ALREADY_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, "P005", "이미 삭제된 상품입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "P006", "인가되지 않는 요청입니다."),
    PRODUCT_ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, "P007", "이미 존재하는 상품입니다."),
    INVALID_SORT_EXCEPTION(HttpStatus.BAD_REQUEST, "P008", "올바르지 않은 정렬 값입니다."),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "P009", "존재하지 않는 사용자입니다."),
    PROMOTION_INVALID_QUANTITY_EXCEPTION(HttpStatus.BAD_REQUEST, "P010", "상품의 재고 수량보다 많은 요청값입니다."),
    PROMOTION_INVALID_PRICE_EXCEPTION(HttpStatus.BAD_REQUEST, "P011", "상품의 기존 가격 이상인 요청값입니다."),
    PROMOTION_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "P012", "존재하지 않는 타임세일입니다."),
    PROMOTION_DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST, "P013", "동일한 시간대에 해당 상품에 대한 타임세일이 이미 존재합니다."),
    PROMOTION_IS_ACTIVE_EXCEPTION(HttpStatus.BAD_REQUEST, "P014", "현재 진행 중인 타임세일입니다."),
    REVIEW_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "P015", "존재하지 않는 리뷰입니다."),
    COMPANY_INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, "PC016", "올바르지 않은 업체 요청 값입니다."),
    COMPANY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "PC017", "존재하지 않는 업체입니다."),
    ORDER_INVALID_VALUE_EXCEPTION(HttpStatus.NOT_FOUND, "O018", "올바르지 않은 주문 요청 값입니다."),
    ORDER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "O019", "존재하지 않는 주문입니다."),
    PRODUCT_CONCURRENT_MODIFICATION_EXCEPTION(HttpStatus.BAD_REQUEST, "P020", "상품의 재고를 업데이트하는 과정에서 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}