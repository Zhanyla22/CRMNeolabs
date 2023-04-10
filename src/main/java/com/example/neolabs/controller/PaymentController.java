package com.example.neolabs.controller;

import com.example.neolabs.controller.base.BaseController;
import com.example.neolabs.dto.PaymentDto;
import com.example.neolabs.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController extends BaseController {

    final PaymentService paymentService;

    @Operation(summary = "Make a payment for monthly bill")
    @PostMapping("/pay")
    public ResponseEntity<PaymentDto> makeSinglePayment(@RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(paymentService.pay(paymentDto));
    }

    @Operation(summary = "Get a payment by Id")
    @GetMapping("{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("paymentId") Long paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
}
