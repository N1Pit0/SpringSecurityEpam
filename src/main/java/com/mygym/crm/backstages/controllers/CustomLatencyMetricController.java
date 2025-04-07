package com.mygym.crm.backstages.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("custom-metrics")
public class CustomLatencyMetricController {
    private final Timer timer;

    public CustomLatencyMetricController(MeterRegistry meterRegistry) {
        this.timer = Timer.builder("http_request_latency")
                .description("Time taken to process HTTP requests")
                .tags("endpoint", "custom-latency")
                .register(meterRegistry);
    }

    @GetMapping("/monitor-latency-api")
    public ResponseEntity<String> monitorLatencyApi() {
        return timer.record(() -> {
            // Simulate request processing
            try {
                Thread.sleep(500); // Simulate processing delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new ResponseEntity<>("simulated api", HttpStatus.OK);
        });
    }

    @GetMapping("/monitor-latency-query")
    public ResponseEntity<String> monitorLatencyQuery() {
        Random random = new Random();

        timer.record(() -> {
            try {
                // Simulate query latency between 100ms and 2000ms
                Thread.sleep(random.nextInt(1900) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return new ResponseEntity<>("simulated query", HttpStatus.OK);
    }
}
