package com.harsh.ecommerce.ShippingService.repository;

import com.harsh.ecommerce.ShippingService.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}