package com.ecostream.notification.Kafka;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;
import com.ecostream.notification.strategy.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationConsumer {

    private final Map<ShipmentStatus, NotificationHandler> handlerMap;

    @Autowired
    public NotificationConsumer(List<NotificationHandler> handlers) {

        handlers.forEach(h -> System.out.println("   -> Loaded Strategy: " + h.getClass().getSimpleName()));


        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(NotificationHandler::getSupportStatus, Function.identity()));
    }

    @KafkaListener(topics = "shipment-events", groupId = "notification-group-FINAL-V2")
    public void receiveMessage(ShipmentDTO message) {
        if (message == null) return;
        NotificationHandler handler = handlerMap.get(message.status());

        if (handler != null) {
            handler.handle(message);
        } else {
            System.out.println("⚠️ No strategy found for status: " + message.status() + ". Skipping.");
        }
    }
}