package com.payment.procesor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@Entity
@Table(name="payments")
public class PaymentEntity {

 @Id
 private String paymentId;
 private String accountId;
 private BigDecimal amount;
 private String status;
 private Instant createdAt;
 private Instant processedAt;
}