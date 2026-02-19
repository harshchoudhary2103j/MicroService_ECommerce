package com.harsh.ecommerce.ShippingService.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    private Long orderId;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}

