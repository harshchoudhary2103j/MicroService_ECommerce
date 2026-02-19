package com.harsh.ecommerce.OrderService.clients;

import com.harsh.ecommerce.OrderService.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "InventoryService")
public interface InventoryOpenFeignClient {

    @PutMapping("/inventory/products/reduce-stocks")
    Double reduceStocks(OrderRequestDto orderRequestDto);

    //cancel order
    @PutMapping("inventory/products/refuel-stocks")
    void refuelStocks(OrderRequestDto orderRequestDto);

}
