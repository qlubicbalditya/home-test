package com.nutech.hometest.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nutech.hometest.dto.BalanceData;
import com.nutech.hometest.dto.TransactionData;
import com.nutech.hometest.dto.TransactionRecordData;
import com.nutech.hometest.entities.TransactionEntity;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, String>{
    
    @Query(nativeQuery = true,
        value = "SELECT COALESCE(SUM(amount), 0) AS balance"
        +" FROM t_balance"
        +" WHERE profile_id = :profileId")
    BalanceData findDataBalance(@Param("profileId") String profileId);

    @Modifying
    @Query(nativeQuery = true,
        value = "INSERT INTO t_balance(id, amount, profile_id, top_up_date)"
        +" VALUES(:id, :amount, :profileId, :topUpDate)")
    void saveDataBalance(@Param("id") String id, 
        @Param("amount") Integer amount, 
        @Param("profileId") String profileId, 
        @Param("topUpDate") LocalDateTime topUpDate);

    @Modifying
    @Query(nativeQuery = true,
        value = "INSERT INTO public.t_transaction(id, invoice_number, service_id, total_amount, transaction_type, created_on, profile_id)"
        +" VALUES(:id, :invoiceNumber, :serviceId, :totalAmount, :transactionType, :createdOn, :profileId)")
    void saveDataTransaction(@Param("id") String id, 
        @Param("invoiceNumber") String invoiceNumber,
        @Param("serviceId") String serviceId,
        @Param("totalAmount") Integer totalAmount, 
        @Param("transactionType") String transactionType, 
        @Param("createdOn") LocalDateTime createdOn, 
        @Param("profileId") String profileId);

    @Query(nativeQuery = true,
        value = "SELECT tt.invoice_number AS invoiceNumber, srv.service_code AS serviceCode, srv.service_name AS serviceName,"
        +" tt.transaction_type AS transactionType, tt.total_amount AS totalAmount, tt.created_on AS createdOn"
        +" FROM t_transaction tt"
        +" JOIN m_service srv ON tt.service_id = srv.id"
        +" WHERE tt.id = :id")
    TransactionData findDataTransaction(@Param("id") String id);

    @Query(nativeQuery = true,
        value = "SELECT tt.invoice_number AS invoiceNumber, tt.transaction_type AS transactionType,"
        +" COALESCE(srv.service_name, 'Top Up Balance') AS description, tt.total_amount AS totalAmount, tt.created_on AS createdOn"
        +" FROM t_transaction tt"
        +" LEFT JOIN m_service srv ON tt.service_id = srv.id"
        +" WHERE tt.profile_id = :profileId"
        +" ORDER BY tt.created_on DESC"
        +" LIMIT :limit OFFSET (:offset - 1) * :limit")
    List<TransactionRecordData> findDataTransactionHistory(@Param("profileId") String profileId, 
        @Param("offset") int offset,
        @Param("limit") int limit);

    
}
