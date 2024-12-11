package com.nutech.hometest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.hometest.dto.BalanceData;
import com.nutech.hometest.dto.TopUpInput;
import com.nutech.hometest.dto.TransactionData;
import com.nutech.hometest.dto.TransactionHistoryData;
import com.nutech.hometest.dto.TransactionInput;
import com.nutech.hometest.services.TransactionService;
import com.nutech.hometest.supports.BasicResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Module Transaction")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionApi {
    
    @Autowired
    private TransactionService service;

    @Operation(summary = "Balance")
    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<BalanceData>> getBalance(
        @AuthenticationPrincipal String email
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        BalanceData data = service.getDataBalance(email);
        return ResponseEntity.ok(new BasicResponse<BalanceData>(data, "Sukses"));
    }

    @Operation(summary = "Top Up")
    @PostMapping(value = "/topup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<BalanceData>> topup(
        @AuthenticationPrincipal String email,
        @RequestBody(required = true) @Valid TopUpInput input
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        BalanceData data = service.topUpAmount(input, email);
        return ResponseEntity.ok(new BasicResponse<BalanceData>(data, "Sukses"));
    }

    @Operation(summary = "Transaction")
    @PostMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<TransactionData>> transaction(
        @AuthenticationPrincipal String email,
        @RequestBody(required = true) @Valid TransactionInput input
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        TransactionData data = service.createTransaction(input, email);
        if (data == null) {
            return new ResponseEntity<>(new BasicResponse<>(102, "Saldo tidak mencukupi"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new BasicResponse<TransactionData>(data, "Sukses"));
    }

    @Operation(summary = "Transaction History")
    @GetMapping(value = "/transaction/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<TransactionHistoryData>> transactionHistories(
        @AuthenticationPrincipal String email,
        @RequestParam(value = "offset") int offset,
        @RequestParam(value = "limit") int limit
    )throws Exception{
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        TransactionHistoryData data = service.transactionHistories(email, offset+1, limit);
        return ResponseEntity.ok(new BasicResponse<TransactionHistoryData>(data, "Sukses"));
    }
}
