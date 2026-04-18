package com.payment.procesor.repository;

import com.payment.procesor.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
 List<PaymentEntity> findByAccountId(String accountId);
 long countByStatus(String status);
}