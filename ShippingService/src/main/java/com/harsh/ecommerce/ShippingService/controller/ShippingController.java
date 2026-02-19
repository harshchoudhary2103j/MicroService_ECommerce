package com.harsh.ecommerce.ShippingService.controller;

import com.harsh.ecommerce.ShippingService.dto.OrderRequestDto;
import com.harsh.ecommerce.ShippingService.services.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmShipping(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(shippingService.createShipment(orderRequestDto));
    }

}
