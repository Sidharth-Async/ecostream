package com.ecostream.shipment.controller;

import com.ecostream.common.dto.*;
import com.ecostream.shipment.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final com.ecostream.shipment.service.ShipmentService service;

    public ShipmentController(com.ecostream.shipment.service.ShipmentService service) {
        this.service = service;
    }

    @org.springframework.web.bind.annotation.PostMapping
    public ShipmentDTO create(@org.springframework.web.bind.annotation.RequestBody ShipmentDTO request) {
        return service.createShipment(request);
    }

    @GetMapping("/test")
    public ShipmentDTO getTestShipment() {
        // We are creating a fake shipment to test the data structure
        return new ShipmentDTO(
                UUID.randomUUID().toString(),
                "ORDER-123",
                ShipmentStatus.IN_TRANSIT,
                new GeoLocationDTO(40.7128, -74.0060), // New York
                new AddressDTO("5th Avenue", "New York", "10001", "USA"),
                LocalDateTime.now().plusDays(2)
        );
    }

    @GetMapping("/{id}")
    public ShipmentDTO getShipment(@PathVariable ("id") String id) {
        return service.getShipment(id);
    }

    @DeleteMapping("/{id}")
    public String deleteShipment(@PathVariable ("id") String id) {
        service.deleteShipment(id);
        return "Shipment deleted successfully.";
    }


}