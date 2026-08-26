package com.teamb.app2.controller;

import com.teamb.app2.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        metrics.put("service", "app2-java-teamB");

        Map<String, Object> requests = new HashMap<>();
        requests.put("total", metricsService.getRequestCount());
        requests.put("process", metricsService.getProcessCount());
        requests.put("calculate", metricsService.getCalculateCount());
        requests.put("errors", metricsService.getErrorCount());

        Map<String, Object> uptime = new HashMap<>();
        uptime.put("start_time", metricsService.getStartTime().toString());
        uptime.put("uptime_seconds", metricsService.getUptimeSeconds());

        Map<String, Object> metricsData = new HashMap<>();
        metricsData.put("requests", requests);
        metricsData.put("uptime", uptime);

        metrics.put("metrics", metricsData);

        return ResponseEntity.ok(metrics);
    }
}
