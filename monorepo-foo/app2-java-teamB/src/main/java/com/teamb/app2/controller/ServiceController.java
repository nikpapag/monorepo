package com.teamb.app2.controller;

import com.teamb.app2.model.ServiceInfo;
import com.teamb.app2.model.ApiResponse;
import com.teamb.app2.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ServiceController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        metricsService.incrementRequestCount();
        ServiceInfo info = new ServiceInfo(
            "app2-java-teamB",
            "TeamB",
            "running",
            "lightweight",
            appVersion
        );
        return ResponseEntity.ok(info);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/process")
    public ResponseEntity<ApiResponse> processData(@RequestBody Map<String, Object> request) {
        metricsService.incrementRequestCount();
        metricsService.incrementProcessCount();

        String input = (String) request.getOrDefault("input", "");
        String processed = input.toUpperCase();

        ApiResponse response = new ApiResponse(
            "success",
            "Data processed successfully",
            Map.of("original", input, "processed", processed)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculate(
            @RequestParam(defaultValue = "10") int a,
            @RequestParam(defaultValue = "20") int b) {

        metricsService.incrementRequestCount();
        metricsService.incrementCalculateCount();

        Map<String, Object> result = new HashMap<>();
        result.put("a", a);
        result.put("b", b);
        result.put("sum", a + b);
        result.put("product", a * b);
        result.put("difference", a - b);

        return ResponseEntity.ok(result);
    }
}
