package com.payment.common;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentEvent {
    @NotBlank
    public String paymentId;
    @NotBlank
    public String accountId;
    @NotNull
    @Positive
    public BigDecimal amount;
    @NotNull
    public Instant createdAt;
}