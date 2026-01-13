package com.ecostream.common.dto;

import java.time.LocalDateTime;

public record LocationUpdate(
        String shipmentId,
        double latitude,
        double longitude,
        LocalDateTime timestamp
) {}
