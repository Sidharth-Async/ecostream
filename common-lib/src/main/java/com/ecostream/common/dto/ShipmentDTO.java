package com.ecostream.common.dto;

import java.time.LocalDateTime;

public record ShipmentDTO(
        String id,                 // UUID string
        String orderId,            // The link to the Order
        ShipmentStatus status,     // The Enum we just made
        GeoLocationDTO currentLocation, // The Record we just made
        AddressDTO destination,    // The Address Record
        LocalDateTime estimatedArrival
) {}
