package com.ecostream.notification.Kafka;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    EmailService  emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "shipment-events", groupId = "notification-group")
    public void receiveMessage(String message) {
       /* if(message.status() == ShipmentStatus.DELIVERED){
            emailService.sendDeliveryNotification("sidharth@gmail.com", message.orderId());
        }*/

        System.out.println(">> [DEBUG] Raw JSON Received: " + message);
    }
}
