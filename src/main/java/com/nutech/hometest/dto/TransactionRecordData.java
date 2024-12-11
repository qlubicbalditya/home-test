package com.nutech.hometest.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nutech.hometest.supports.TransactionTypeEnum;

@JsonPropertyOrder({"invoice_number", "transaction_type", "description", "total_amount", "created_on"})
public interface TransactionRecordData {
    
    @JsonProperty(value = "invoice_number")
    String getInvoiceNumber();

    @JsonProperty(value = "transaction_type")
    TransactionTypeEnum getTransactionType();
    
    String getDescription();

    @JsonProperty(value = "total_amount")
    Integer getTotalAmount();

    @JsonProperty(value = "created_on")
    LocalDateTime getCreatedOn();
}
