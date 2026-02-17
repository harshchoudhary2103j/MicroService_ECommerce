package com.harsh.ecommerce.InventoryService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "OrderService", path = "/orders")
public interface OrdersClient {
    @GetMapping("/core/helloOrders")
    String helloOrders();

}
