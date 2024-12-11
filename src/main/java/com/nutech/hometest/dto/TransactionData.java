package com.nutech.hometest.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nutech.hometest.supports.TransactionTypeEnum;

@JsonPropertyOrder({"invoice_number", "service_code", "service_name", "transaction_type", "total_amount", "created_on"})
public interface TransactionData {
    
    @JsonProperty(value = "invoice_number")
    String getInvoiceNumber();

    @JsonProperty(value = "service_code")
    String getServiceCode();

    @JsonProperty(value = "service_name")
    String getServiceName();

    @JsonProperty(value = "transaction_type")
    TransactionTypeEnum getTransactionType();

    @JsonProperty(value = "total_amount")
    Integer getTotalAmount();

    @JsonProperty(value = "created_on")
    LocalDateTime getCreatedOn();
}
