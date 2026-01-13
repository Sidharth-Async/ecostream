package com.ecostream.shipment.kafka;

import com.ecostream.common.dto.LocationUpdate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LocationConsumer {

    @KafkaListener(topics = "tracking-updates", groupId = "shipment-group")
    public void consume(LocationUpdate update) {
        System.out.println("==========================================");
        System.out.println("SHIPMENT SERVICE RECEIVED DATA!");
        System.out.println("Shipment ID: " + update.shipmentId());
        System.out.println("New GPS: " + update.latitude() + ", " + update.longitude());
        System.out.println("Timestamp: " + update.timestamp());
        System.out.println("==========================================");

        // Next week: We will update the Database here!
    }
}