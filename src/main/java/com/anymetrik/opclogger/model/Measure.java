package com.anymetrik.opclogger.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "measure")
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_time")
    private Instant deviceTime;

    @Column
    private Double value;

    @Column(name = "device_fk")
    private Long deviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Instant deviceTime) {
        this.deviceTime = deviceTime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Measure(Long deviceId, Double value, Instant deviceTime) {
        this.deviceId = deviceId;
        this.value = value;
        this.deviceTime = deviceTime;
    }
}
