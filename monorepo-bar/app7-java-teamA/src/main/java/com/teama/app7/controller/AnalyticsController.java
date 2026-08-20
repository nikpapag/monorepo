package com.teama.app7.controller;

import com.teama.app7.model.AnalyticsRequest;
import com.teama.app7.model.AnalyticsResult;
import com.teama.app7.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("service", "app7-java-teamA");
        info.put("team", "TeamA");
        info.put("status", "running");
        info.put("type", "resource-intensive-analytics");
        info.put("version", appVersion);
        info.put("pipeline", "custom");
        return ResponseEntity.ok(info);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/process")
    public ResponseEntity<AnalyticsResult> processAnalytics(@RequestBody AnalyticsRequest request) {
        AnalyticsResult result = analyticsService.processLargeDataset(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/aggregate")
    public ResponseEntity<AnalyticsResult> aggregateData(@RequestBody AnalyticsRequest request) {
        AnalyticsResult result = analyticsService.aggregateData(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/compute/{iterations}")
    public ResponseEntity<Map<String, Object>> computeIntensive(@PathVariable int iterations) {
        long startTime = System.currentTimeMillis();
        double result = analyticsService.performIntensiveComputation(iterations);
        long duration = System.currentTimeMillis() - startTime;

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("iterations", iterations);
        response.put("duration_ms", duration);

        return ResponseEntity.ok(response);
    }
}
