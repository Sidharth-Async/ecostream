package com.ecostream.notification.strategy;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.notification.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class DeliveredHandler implements NotificationHandler {
    private final EmailService emailService;
    public DeliveredHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ShipmentStatus getSupportStatus() {
        return ShipmentStatus.DELIVERED;
    }
    @Override
    public void handle(ShipmentDTO shipment){
        emailService.sendDeliveryNotification("sidharthyadav134134@gmail.com", shipment.orderId());
    }

}
