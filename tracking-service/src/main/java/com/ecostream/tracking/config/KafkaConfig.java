package com.ecostream.tracking.config;

import com.ecostream.common.dto.LocationUpdate;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, LocationUpdate> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 1. Create a mapper and tell it NOT to write dates as arrays
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 2. Create the serializer with this custom mapper
        JsonSerializer<LocationUpdate> valueSerializer = new JsonSerializer<>(mapper);

        return new DefaultKafkaProducerFactory<>(
                configProps,
                new StringSerializer(),
                valueSerializer
        );
    }

    @Bean
    public KafkaTemplate<String, LocationUpdate> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}