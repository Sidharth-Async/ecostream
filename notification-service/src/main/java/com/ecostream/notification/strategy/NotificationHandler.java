package com.ecostream.notification.strategy;

import com.ecostream.common.dto.ShipmentDTO;
import com.ecostream.common.dto.ShipmentStatus;

public interface NotificationHandler {

    ShipmentStatus getSupportStatus();

    void handle(ShipmentDTO shipment);
}
