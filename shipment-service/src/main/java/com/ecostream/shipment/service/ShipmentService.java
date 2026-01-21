package com.ecostream.shipment.service;

import com.ecostream.common.dto.GeoLocationDTO;
import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.shipment.model.ShipmentEntity;
import com.ecostream.shipment.repository.ShipmentRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final StringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public ShipmentService(ShipmentRepository shipmentRepository, StringRedisTemplate redisTemplate, KafkaTemplate<String, Object> kafkaTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
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
                initialData.estimatedArrival(),
                null, null, null
        );
        ShipmentEntity saved = shipmentRepository.save(entity);

        return new ShipmentDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getStatus(),
                null, null,
                saved.getEstimatedArrival()
        );
    }

    public ShipmentDTO getShipment(String shipmentId) {

        ShipmentEntity foundEntity = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("shipment not found"));


        Double finalLat = foundEntity.getCurrentLatitude();
        Double finalLon = foundEntity.getCurrentLongitude();


        String redisKey = "shipment:location:" + shipmentId;
        String redisData = redisTemplate.opsForValue().get(redisKey);

        if (redisData != null) {
            String[] parts = redisData.split(",");
            if (parts.length == 2) {
                finalLat = Double.parseDouble(parts[0]);
                finalLon = Double.parseDouble(parts[1]);
                System.out.println("   [Cache Hit] Served location from Redis for " + shipmentId);
            }
        }

        GeoLocationDTO locationObj = null;
        if (finalLat != null && finalLon != null) {
            locationObj = new GeoLocationDTO(finalLat, finalLon);
        }

        return new ShipmentDTO(
                foundEntity.getId(),
                foundEntity.getOrderId(),
                foundEntity.getStatus(),
                locationObj,
                null,
                foundEntity.getEstimatedArrival()
        );
    }

    public void deleteShipment(String shipmentId) {
        ShipmentEntity foundEntity = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("shipment does not exists"));
        shipmentRepository.delete(foundEntity);
    }

    public ShipmentDTO updateShipmentStatus(String  shipmentId, ShipmentStatus status) {
        // finds the shipment id:
        ShipmentEntity entity = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("shipment does not exists"));

        entity.setStatus(status);
        ShipmentEntity saved = shipmentRepository.save(entity);

        GeoLocationDTO locationObj = null;
        if (saved.getEstimatedArrival().isBefore(LocalDateTime.now())) {
            locationObj = new GeoLocationDTO(saved.getCurrentLatitude(), saved.getCurrentLongitude());
        }

        ShipmentDTO dto = new ShipmentDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getStatus(),
                locationObj,
                null,
                saved.getEstimatedArrival()
        );
        kafkaTemplate.send("shipment-events", dto);
        System.out.println("   >> [Kafka] Sent event: " + status + " for " + shipmentId);
        return dto;
    }

}