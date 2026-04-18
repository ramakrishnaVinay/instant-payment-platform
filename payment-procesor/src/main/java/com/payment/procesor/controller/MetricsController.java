
package com.payment.procesor.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.procesor.service.MetricsService;


import lombok.RequiredArgsConstructor;

@RestController

@RequestMapping("/api/metrics")

@RequiredArgsConstructor
public class MetricsController {

	private final MetricsService metricService = new MetricsService();

	@GetMapping("/summary")
	public Map<String, Object> summary() {
		return metricService.summary();
	}
}
