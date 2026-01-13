package com.ecostream.tracking.service;

import com.ecostream.common.dto.LocationUpdate;
import com.ecostream.tracking.kafka.LocationProducer;
import com.ecostream.tracking.repository.TrackingRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    private final TrackingRepository repository;
    private final LocationProducer producer;
    public TrackingService(TrackingRepository repository, LocationProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public void updateLocation(LocationUpdate update) {
        repository.save(update);
        producer.sendLocation(update);
    }

    public String getCurrentLocation(String shipmentId) {
        return repository.getLatestLocation(shipmentId);
    }
}
