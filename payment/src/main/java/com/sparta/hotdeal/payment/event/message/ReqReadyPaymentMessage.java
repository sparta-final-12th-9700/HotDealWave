package com.sparta.hotdeal.payment.event.message;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqReadyPaymentMessage {
    private UUID orderId;
}
