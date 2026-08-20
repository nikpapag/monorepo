package com.teama.app7.model;

public class AnalyticsRequest {
    private int dataSize;
    private String operation;

    public AnalyticsRequest() {}

    public AnalyticsRequest(int dataSize, String operation) {
        this.dataSize = dataSize;
        this.operation = operation;
    }

    public int getDataSize() { return dataSize; }
    public void setDataSize(int dataSize) { this.dataSize = dataSize; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
