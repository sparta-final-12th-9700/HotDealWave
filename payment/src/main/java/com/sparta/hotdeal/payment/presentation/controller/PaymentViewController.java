package com.sparta.hotdeal.payment.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    /* 삭제 예정
    @GetMapping
    public String getPaymentPage() {
        return "payment";
    }
    */

    @GetMapping("/success")
    public String getPaymentSuccessPage() {
        return "payment-success";
    }

    @GetMapping("/fail")
    public String getPaymentFailPage() {
        return "payment-fail";
    }

    @GetMapping("/cancel")
    public String getPaymentCancelPage() {
        return "payment-cancel";
    }
}
