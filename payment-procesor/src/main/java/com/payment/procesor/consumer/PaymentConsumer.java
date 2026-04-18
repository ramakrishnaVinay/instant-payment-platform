package com.payment.procesor.consumer;

import com.payment.procesor.common.PaymentEvent;
import com.payment.procesor.common.PaymentOutcome;
import com.payment.procesor.entity.PaymentOutcomeEntity;
import com.payment.procesor.repository.PaymentRepository;
import com.payment.procesor.service.MetricsService;
import com.payment.procesor.service.PaymentProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PaymentConsumer {
     
    private final KafkaTemplate<String, PaymentOutcome> kafkaTemplate = null;
	
	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private MetricsService metricsService;

	@KafkaListener(topics = "payments", groupId = "processor-group")
	public void consume(PaymentEvent event) {

		long start = System.currentTimeMillis();

		String status;

		// BUSINESS RULES
		if (event.getDebitAccountId().equals(event.getCreditAccountId())) {
			status = "REJECTED";
		} else if (event.getAmount().compareTo(BigDecimal.valueOf(250000)) > 0) {
			status = "HELD";
		} else {
			status = "PROCESSED";
		}

		long time = System.currentTimeMillis() - start;

		// SAVE TO DB
		PaymentOutcomeEntity entity = new PaymentOutcomeEntity();
		entity.setPaymentId(event.getPaymentId());
		entity.setDebitAccountId(event.getDebitAccountId());
		entity.setCreditAccountId(event.getCreditAccountId());
		entity.setAmount(event.getAmount());
		entity.setCurrency(event.getCurrency());
		entity.setStatus(status);
		entity.setProcessedAt(Instant.now());
		entity.setProcessingTimeMs(time);

		repository.save(entity);

		// UPDATE METRICS
		metricsService.record(status, time);

		// PUBLISH RESULT
		PaymentOutcome outcome = new PaymentOutcome();
		outcome.setPaymentId(event.getPaymentId());
		outcome.setStatus(status);
		outcome.setProcessedAt(Instant.now());

		kafkaTemplate.send("payments.processed", event.getDebitAccountId(), outcome);
	}
	// service.process(event);
	// }
}