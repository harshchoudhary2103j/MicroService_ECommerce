package com.harsh.ecommerce.OrderService.controller;

import com.harsh.ecommerce.OrderService.clients.InventoryOpenFeignClient;
import com.harsh.ecommerce.OrderService.dto.OrderRequestDto;
import com.harsh.ecommerce.OrderService.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello from Order Service";
    }
    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);


    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest) {
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);  // Returns 200 OK with the list of orders
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {} via controller", id);
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);  // Returns 200 OK with the order
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderRequestDto> cancelOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @PostMapping("/ship/{id}")
    public ResponseEntity<OrderRequestDto> shipOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.shipOrder(id));
    }


}
