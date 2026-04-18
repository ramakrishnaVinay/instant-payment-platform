package com.payment.procesor.repository;

import com.payment.procesor.entity.PaymentOutcomeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentOutcomeEntity, Long> {

    Page<PaymentOutcomeEntity> findByStatus(String status, Pageable pageable);

   
    //List<PaymentOutcomeEntity> findHistory(@Param("accountId") String accountId);

	long countByStatus(String string);
	
    boolean existsByPaymentId(UUID paymentId);

    @Query("""
        SELECT p FROM PaymentOutcomeEntity p
        WHERE (:status IS NULL OR p.status = :status)
        AND (:accountId IS NULL OR 
             p.debitAccountId = :accountId OR 
             p.creditAccountId = :accountId)
    """)
    Page<PaymentOutcomeEntity> findWithFilters(
            @Param("status") String status,
            @Param("accountId") String accountId,
            Pageable pageable
    );
    
    @Query("""
    	    SELECT 
    	        COUNT(p),
    	        SUM(p.amount),
    	        MIN(p.processedAt),
    	        MAX(p.processedAt)
    	    FROM PaymentOutcomeEntity p
    	""")
    	Object[] getSummaryRaw();
}