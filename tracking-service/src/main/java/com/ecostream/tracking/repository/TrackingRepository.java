package com.ecostream.tracking.repository;

import com.ecostream.common.dto.LocationUpdate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Repository
public class TrackingRepository {

    private final StringRedisTemplate redisTemplate;

    public TrackingRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void save(LocationUpdate update) {
        String key = "shipment:" + update.shipmentId();


        String value = String.format("%s,%s,%s,%s",
                update.latitude(),
                update.longitude(),
                update.timestamp(),
                update.status()
        );

        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
    }


    public LocationUpdate getLatestLocation(String shipmentId) {
        String key = "shipment:" + shipmentId;
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }


        String[] parts = value.split(",");

        double lat = Double.parseDouble(parts[0]);
        double lon = Double.parseDouble(parts[1]);
        LocalDateTime timeStr = parts.length == 2 ? LocalDateTime.parse(parts[2]) : LocalDateTime.now();
        String status = parts[3];


        return new LocationUpdate(
                shipmentId,
                status,
                lat,
                lon,
                timeStr
        );
    }
}