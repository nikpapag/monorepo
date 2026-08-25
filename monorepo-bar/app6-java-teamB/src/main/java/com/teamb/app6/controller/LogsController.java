package com.teamb.app6.controller;

import com.teamb.app6.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogsController {

    @Autowired
    private RequestLogService requestLogService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLogs(
            @RequestParam(defaultValue = "20") int limit) {

        List<RequestLogService.RequestLogEntry> logs = requestLogService.getRecentLogs(limit);

        Map<String, Object> response = new HashMap<>();
        response.put("service", "app6-java-teamB");
        response.put("total_requests", requestLogService.getTotalRequestCount());
        response.put("logs", logs);

        return ResponseEntity.ok(response);
    }
}
