package com.ecostream.common.dto;

import java.time.LocalDateTime;

public record TrackingDTO(
        String orderId,
        String status,
        Double latitude,
        Double longitude,
        LocalDateTime lastUpdated
) {}
