package com.teama.app7.model;

import java.util.Map;

public class AnalyticsResult {
    private String status;
    private Map<String, Object> data;
    private long processingTimeMs;

    public AnalyticsResult() {}

    public AnalyticsResult(String status, Map<String, Object> data, long processingTimeMs) {
        this.status = status;
        this.data = data;
        this.processingTimeMs = processingTimeMs;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }

    public long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(long processingTimeMs) { this.processingTimeMs = processingTimeMs; }
}
