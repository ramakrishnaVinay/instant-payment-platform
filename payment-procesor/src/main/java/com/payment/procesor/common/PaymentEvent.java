package com.payment.procesor.common;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentEvent {
    private UUID paymentId;
    private String debitAccountId;
    private String creditAccountId;
    private BigDecimal amount;
    public UUID getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}
	public String getDebitAccountId() {
		return debitAccountId;
	}
	public void setDebitAccountId(String debitAccountId) {
		this.debitAccountId = debitAccountId;
	}
	public String getCreditAccountId() {
		return creditAccountId;
	}
	public void setCreditAccountId(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	private String currency;
    private String reference;
    private Instant timestamp;
	public BigDecimal getAmount() {
		// TODO Auto-generated method stub
		return null;
	}
}