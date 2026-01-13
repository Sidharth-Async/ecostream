package com.ecostream.tracking.controller;

import com.ecostream.common.dto.LocationUpdate;
import com.ecostream.tracking.service.TrackingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    private final TrackingService service;
    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @PostMapping("/locations")
    public String updateLocation(@RequestBody LocationUpdate update) {
        service.updateLocation(update);
        return "Location successfully updated";
    }

    @GetMapping("/{id}")
    public String getLocation(@PathVariable("id") String id) {
        return service.getCurrentLocation(id);
    }
}