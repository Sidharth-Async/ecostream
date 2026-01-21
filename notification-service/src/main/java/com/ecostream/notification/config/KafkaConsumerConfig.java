package com.ecostream.notification.config;

import com.ecostream.common.dto.ShipmentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, ShipmentDTO> consumerFactory() {
        // 1. Create a smart ObjectMapper that can read Dates
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 2. Create the Deserializer using that mapper
        JsonDeserializer<ShipmentDTO> jsonDeserializer = new JsonDeserializer<>(ShipmentDTO.class, objectMapper);
        jsonDeserializer.addTrustedPackages("*"); // Trust all packages
        jsonDeserializer.setUseTypeMapperForKey(true);

        // 3. Kafka Configuration
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // NEW GROUP ID to skip the crashing message at offset 11
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group-FINAL-V2");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer // Pass our smart deserializer here
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShipmentDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ShipmentDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}