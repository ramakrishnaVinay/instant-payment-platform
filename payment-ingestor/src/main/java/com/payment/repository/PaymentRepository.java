package com.payment.repository;

import com.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    
    // Positive query - payments matching both debit and credit accounts
    @Query("SELECT p FROM Payment p WHERE p.debitAccountId = :debitAccountId AND p.creditAccountId = :creditAccountId")
    List<Payment> findByDebitAccountIdAndCreditAccountId(@Param("debitAccountId") String debitAccountId, @Param("creditAccountId") String creditAccountId);
    
    // Payments NOT from a specific debit account
    List<Payment> findByDebitAccountIdNot(String debitAccountId);
    
    // Payments NOT to a specific credit account
    List<Payment> findByCreditAccountIdNot(String creditAccountId);
    
    // Basic queries
    List<Payment> findByDebitAccountId(String debitAccountId);
    
    List<Payment> findByCreditAccountId(String creditAccountId);
}
