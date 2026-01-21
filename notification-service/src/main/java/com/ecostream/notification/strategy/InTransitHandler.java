package com.ecostream.notification.strategy;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import org.springframework.stereotype.Component;

@Component
public class InTransitHandler implements NotificationHandler {

    @Override
    public ShipmentStatus getSupportStatus(){
        return ShipmentStatus.IN_TRANSIT;
    }

    @Override
    public void handle(ShipmentDTO shipment){
        System.out.println("[Support] Handling inTransit status.");
        System.out.println("Location: " + shipment.currentLocation());
        System.out.println("EstimatedArrival: " + shipment.estimatedArrival());
    }
}
