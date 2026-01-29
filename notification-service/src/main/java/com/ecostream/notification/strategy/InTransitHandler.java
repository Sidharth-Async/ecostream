package com.ecostream.notification.strategy;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.notification.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class InTransitHandler implements NotificationHandler {

    private final EmailService emailService;

    public InTransitHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ShipmentStatus getSupportStatus(){
        return ShipmentStatus.IN_TRANSIT;
    }

    @Override
    public void handle(ShipmentDTO shipment) {
        System.out.println("🚚 [Strategy] Handling IN_TRANSIT Event");

        // Extract location string safely
        String locationString = (shipment.currentLocation() != null)
                ? shipment.currentLocation().toString()
                : "Unknown Location";

        emailService.sendInTransitNotification("sidharthyadav134134@gmail.com", shipment.orderId(), locationString);
    }
}
