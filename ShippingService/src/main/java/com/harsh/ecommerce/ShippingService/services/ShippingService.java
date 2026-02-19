package com.harsh.ecommerce.ShippingService.services;

import com.harsh.ecommerce.ShippingService.dto.OrderRequestDto;
import com.harsh.ecommerce.ShippingService.entity.Shipment;
import com.harsh.ecommerce.ShippingService.entity.ShipmentStatus;
import com.harsh.ecommerce.ShippingService.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {
    private final ShipmentRepository shipmentRepository;

    public String createShipment(OrderRequestDto orderRequestDto){

        Shipment shipment = Shipment.builder()
                .orderId(orderRequestDto.getId())
                .totalAmount(orderRequestDto.getTotalPrice().doubleValue())
                .status(ShipmentStatus.CREATED)
                .build();

        shipmentRepository.save(shipment);

        return "Shipment created for order id: " + orderRequestDto.getId();
    }
}
