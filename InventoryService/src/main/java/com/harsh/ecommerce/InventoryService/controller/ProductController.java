package com.harsh.ecommerce.InventoryService.controller;

import com.harsh.ecommerce.InventoryService.clients.OrdersClient;
import com.harsh.ecommerce.InventoryService.dto.OrderRequestDto;
import com.harsh.ecommerce.InventoryService.dto.ProductDto;
import com.harsh.ecommerce.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersClient ordersClient;

    @GetMapping("/fetchFromOrder")
    public String fetchFromOrder(){
//        ServiceInstance serviceInstance = discoveryClient
//                .getInstances("OrderService")
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("OrderService not available"));
//
//
//        return restClient.get()
//                .uri(serviceInstance.getUri()+"/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return ordersClient.helloOrders();

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>>getAllInventory(){
        List<ProductDto>products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable  Long id){
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

}
