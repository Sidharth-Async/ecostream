package com.ecostream.tracking.kafka;

import com.ecostream.common.dto.LocationUpdate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LocationProducer {

    // Logger allows us to see output in the console without using System.out.println
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationProducer.class);

    // KafkaTemplate is the Spring tool for sending messages
    private final KafkaTemplate<String, LocationUpdate> kafkaTemplate;

    public LocationProducer(KafkaTemplate<String, LocationUpdate> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLocation(LocationUpdate locationUpdate) {
        // Log that we are trying to send data
        LOGGER.info(String.format("Broadcasting location for ID: %s", locationUpdate.shipmentId()));

        // Create the message.
        // TOPIC: "tracking-updates" is the specific channel we are broadcasting on.
        Message<LocationUpdate> message = MessageBuilder
                .withPayload(locationUpdate)
                .setHeader(KafkaHeaders.TOPIC, "tracking-updates")
                .build();

        // Send it!
        kafkaTemplate.send(message);
    }
}