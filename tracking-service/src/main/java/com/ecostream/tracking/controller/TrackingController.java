package com.ecostream.tracking.controller;

import com.ecostream.common.dto.LocationUpdate;
import com.ecostream.common.dto.TrackingDTO;
import com.ecostream.tracking.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }


    @PostMapping("/location")
    public ResponseEntity<Void> updateLocation(@RequestBody LocationUpdate update) {
        service.updateLocation(update);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") String orderId) { // 👈 FIX HERE
        try {
            TrackingDTO data = service.getCurrentLocation(orderId);
            return ResponseEntity.ok(data);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}