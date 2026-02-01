package com.ecostream.shipment.repository;

import com.ecostream.shipment.model.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity,String> {

}
