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

    private LocalDateTime estimatedArrival;

    public ShipmentEntity() {}

    public ShipmentEntity(String id, String orderId, ShipmentStatus status,  LocalDateTime estimatedArrival) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.estimatedArrival = estimatedArrival;
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

    public LocalDateTime getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(LocalDateTime estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }
}
