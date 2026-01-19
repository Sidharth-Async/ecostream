package com.ecostream.shipment.model;

import com.ecostream.common.dto.ShipmentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {

    @Id
    private String id;
    private String orderId;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastUpdated;
    private LocalDateTime estimatedArrival;

    public ShipmentEntity() {}
    public ShipmentEntity(String id, String orderId, ShipmentStatus status,  LocalDateTime estimatedArrival, Double currentLatitude, Double currentLongitude, LocalDateTime lastUpdated) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.estimatedArrival = estimatedArrival;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.lastUpdated = lastUpdated;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(LocalDateTime estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }
}
