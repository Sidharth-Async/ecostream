package com.ecostream.shipment.kafka;

import com.ecostream.common.dto.LocationUpdate;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.shipment.model.ShipmentEntity;
import com.ecostream.shipment.repository.ShipmentRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationConsumer {

    private final ShipmentRepository shipmentRepository;
    private final StringRedisTemplate redisTemplate;

    public LocationConsumer(ShipmentRepository shipmentRepository, StringRedisTemplate redisTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "tracking-updates", groupId = "shipment-group")
    public void consume(LocationUpdate update) {
        String id = update.shipmentId();
        System.out.println(">> Processing GPS for ID: " + id);

        Optional<ShipmentEntity> shipmentEntity = shipmentRepository.findById(id);
        if (shipmentEntity.isPresent()) {
            ShipmentEntity shipmentEntity1 = shipmentEntity.get();
            shipmentEntity1.setCurrentLatitude(update.latitude());
            shipmentEntity1.setCurrentLongitude(update.longitude());
            shipmentEntity1.setLastUpdated(update.timestamp());

            if(shipmentEntity1.getStatus() == ShipmentStatus.DELAYED){
                shipmentEntity1.setStatus(ShipmentStatus.IN_TRANSIT);
            }
            shipmentRepository.save(shipmentEntity1);
            System.out.println("[Updated for id: ]" + id);
        } else {
            ShipmentEntity newShipment = new ShipmentEntity();
            newShipment.setId(id);
            newShipment.setCurrentLatitude(update.latitude());
            newShipment.setCurrentLongitude(update.longitude());
            newShipment.setLastUpdated(update.timestamp());
            newShipment.setStatus(ShipmentStatus.IN_TRANSIT);
            newShipment.setOrderId("unknown");
            shipmentRepository.save(newShipment);
            System.out.println("[Created for id: ]" + id);
        }
        // We create a simple string: "40.7128,-74.0060"
        String redisKey = "shipment:location:" + id;
        String redisValue = update.latitude() + "," + update.longitude();

        // This saves it to the cache instantly
        redisTemplate.opsForValue().set(redisKey, redisValue);
        System.out.println("   [Redis]    Cached location: " + redisValue);
    }
}