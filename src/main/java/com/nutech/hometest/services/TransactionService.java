package com.nutech.hometest.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutech.hometest.dto.BalanceData;
import com.nutech.hometest.dto.TopUpInput;
import com.nutech.hometest.dto.TransactionData;
import com.nutech.hometest.dto.TransactionHistoryData;
import com.nutech.hometest.dto.TransactionRecordData;
import com.nutech.hometest.dto.TransactionInput;
import com.nutech.hometest.entities.TransactionEntity;
import com.nutech.hometest.dto.ProfileData;
import com.nutech.hometest.dto.ServiceData;
import com.nutech.hometest.repositories.ProfileRepo;
import com.nutech.hometest.repositories.ServiceRepo;
import com.nutech.hometest.repositories.TransactionRepo;
import com.nutech.hometest.supports.TransactionTypeEnum;
import com.nutech.hometest.supports.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    @Transactional(readOnly = true)
    public BalanceData getDataBalance(String email) {
        ProfileData profileEntity = profileRepo.findDataByEmail(email);
        return transactionRepo.findDataBalance(profileEntity.getId());
    }

    @Transactional
    public BalanceData topUpAmount(TopUpInput input, String email) {
        ProfileData profileEntity = profileRepo.findDataByEmail(email);
        transactionRepo.saveDataBalance(Utils.createUUID(), input.getTopUpAmount(), profileEntity.getId(), LocalDateTime.now());

        transactionRepo.saveDataTransaction(Utils.createUUID(), generateInvoiceNumber(), null, 
            input.getTopUpAmount(), TransactionTypeEnum.TOPUP.name(), LocalDateTime.now(), profileEntity.getId());

        return transactionRepo.findDataBalance(profileEntity.getId());
    }

    @Transactional(readOnly = true)
    public TransactionHistoryData transactionHistories(String email, int offset, int limit) {
        ProfileData profileEntity = profileRepo.findDataByEmail(email);
        List<TransactionRecordData> records = transactionRepo.findDataTransactionHistory(profileEntity.getId(), offset, limit);
        TransactionHistoryData historyData = new TransactionHistoryData();
        historyData.setOffset(offset-1);
        historyData.setLimit(limit);
        historyData.setRecords(records);

        return historyData;
    }

    @Transactional
    public TransactionData createTransaction(TransactionInput input, String email) {
        ServiceData serviceData = serviceRepo.findDataByServiceCode(input.getServiceCode());
        
        ProfileData profileEntity = profileRepo.findDataByEmail(email);
        BalanceData balanceData = transactionRepo.findDataBalance(profileEntity.getId());
        if (balanceData.getBalance() < serviceData.getServiceTariff()) {
            return null;
        }

        String uuid = Utils.createUUID();
        transactionRepo.saveDataTransaction(uuid, generateInvoiceNumber(), serviceData.getId(), 
            serviceData.getServiceTariff(), TransactionTypeEnum.PAYMENT.name(), LocalDateTime.now(), profileEntity.getId());

        transactionRepo.saveDataBalance(Utils.createUUID(), -serviceData.getServiceTariff(), profileEntity.getId(), LocalDateTime.now());

        return transactionRepo.findDataTransaction(uuid);
        
    }

    public String generateInvoiceNumber() {
        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        
        // Optional<TransactionEntity> lastTransaction = transactionRepo.findAll().stream()
        //         .filter(transaction -> transaction.getCreatedOn().equals(today))
        //         .reduce((first, second) -> second);

        Optional<TransactionEntity> lastTransaction = transactionRepo.findAll().stream()
            .filter(transaction -> transaction.getCreatedOn().toLocalDate().equals(today))  // Hanya bandingkan tanggal
            .max(Comparator.comparing(TransactionEntity::getCreatedOn));  // Ambil transaksi terakhir (berdasarkan waktu)

        int lastNumber = 0;
        if (lastTransaction.isPresent()) {
            String lastInvoiceNumber = lastTransaction.get().getInvoiceNumber();
            String numberPart = lastInvoiceNumber.split("-")[1];
            lastNumber = Integer.parseInt(numberPart);
        }
        
        String newNumber = String.format("%03d", lastNumber + 1);

        log.info("Invoice Number : {} "+newNumber);
        return "INV" + dateString + "-" + newNumber;
    }
}
