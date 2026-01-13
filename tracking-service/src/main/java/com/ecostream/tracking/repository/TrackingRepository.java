package com.ecostream.tracking.repository;

import com.ecostream.common.dto.LocationUpdate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
public class TrackingRepository {

    private final StringRedisTemplate redisTemplate;
    public TrackingRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(LocationUpdate update) {
        String key = "shipment:" + update.shipmentId();
        String value = String.format("%s,%s,%s",
                update.latitude(),
                update.longitude(),
                update.timestamp());
        redisTemplate.opsForValue().set(key, value, 1 , TimeUnit.HOURS);
    }

    public String getLatestLocation(String shipmentId) {
        return redisTemplate.opsForValue().get("shipment:" + shipmentId);
    }
}
