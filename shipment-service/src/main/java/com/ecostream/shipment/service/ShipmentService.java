package com.ecostream.shipment.service;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.shipment.model.ShipmentEntity;
import com.ecostream.shipment.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ShipmentDTO createShipment(ShipmentDTO initialData) {

        LocalDateTime now = LocalDateTime.now();
        if (initialData.estimatedArrival().isBefore(now)) {
            throw new IllegalArgumentException("Arrival cannot be in past!");
        }

        String newId = UUID.randomUUID().toString();
        ShipmentEntity entity = new ShipmentEntity(
                newId,
                initialData.orderId(),
                ShipmentStatus.CREATED,
                initialData.estimatedArrival()
        );
        ShipmentEntity saved = shipmentRepository.save(entity);
        return new ShipmentDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getStatus(),
                null,
                null,
                saved.getEstimatedArrival()
        );
    }

    public ShipmentDTO getShipment(String shipmentId) {

        ShipmentEntity foundEntity = shipmentRepository.findById(shipmentId)
                   .orElseThrow(() -> new RuntimeException("shipment not found"));
        return new ShipmentDTO(
                foundEntity.getId(),
                foundEntity.getOrderId(),
                foundEntity.getStatus(),
                null,
                null,
                foundEntity.getEstimatedArrival()
        );

    }

    public void deleteShipment(String shipmentId) {
        ShipmentEntity foundEntity = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("shipment does not exists"));
        shipmentRepository.delete(foundEntity);
    }

}
