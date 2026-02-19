package com.harsh.ecommerce.OrderService.clients;

import com.harsh.ecommerce.OrderService.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ShippingService")
public interface ShippingOpenFeignClient {
    @PostMapping("/shipping/core/confirm")
    String confirmShipping(@RequestBody OrderRequestDto orderRequestDto);
}
