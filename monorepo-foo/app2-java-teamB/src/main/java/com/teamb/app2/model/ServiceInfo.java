package com.teamb.app2.model;

public class ServiceInfo {
    private String service;
    private String team;
    private String status;
    private String type;
    private String version;

    public ServiceInfo(String service, String team, String status, String type, String version) {
        this.service = service;
        this.team = team;
        this.status = status;
        this.type = type;
        this.version = version;
    }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
