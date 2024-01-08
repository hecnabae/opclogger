package com.anymetrik.opclogger.model;

public class OpcNode {
    private String serverEndPoint;
    private String nodeEndPoint;
    private Double samplinIntervalMillis;

    public String getServerEndPoint() {
        return serverEndPoint;
    }

    public void setServerEndPoint(String serverEndPoint) {
        this.serverEndPoint = serverEndPoint;
    }

    public String getNodeEndPoint() {
        return nodeEndPoint;
    }

    public void setNodeEndPoint(String nodeEndPoint) {
        this.nodeEndPoint = nodeEndPoint;
    }

    public Double getSamplinIntervalMillis() {
        return samplinIntervalMillis;
    }

    public void setSamplinIntervalMillis(Double samplinIntervalMillis) {
        this.samplinIntervalMillis = samplinIntervalMillis;
    }
}
