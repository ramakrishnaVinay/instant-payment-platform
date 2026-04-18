package com.payment.common;
import java.math.BigDecimal;
import java.time.Instant;

public class PaymentEvent {
    public String paymentId;
    public String accountId;
    public BigDecimal amount;
    public Instant createdAt;
}