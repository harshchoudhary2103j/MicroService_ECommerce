package com.harsh.ecommerce.OrderService.service;

import com.harsh.ecommerce.OrderService.clients.InventoryOpenFeignClient;
import com.harsh.ecommerce.OrderService.clients.ShippingOpenFeignClient;
import com.harsh.ecommerce.OrderService.dto.OrderRequestDto;
import com.harsh.ecommerce.OrderService.entity.OrderItem;
import com.harsh.ecommerce.OrderService.entity.OrderStatus;
import com.harsh.ecommerce.OrderService.entity.Orders;
import com.harsh.ecommerce.OrderService.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;
    private final ShippingOpenFeignClient shippingOpenFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }

//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {

        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);
        Orders order = modelMapper.map(orderRequestDto, Orders.class);
        for(OrderItem orderItem: order.getItems()){
            orderItem.setOrder(order);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
       Orders savedOrder =  orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderRequestDto.class);

    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Error while creating order: {}", throwable.getMessage());
        return new OrderRequestDto();
    }

    public OrderRequestDto cancelOrder(Long orderId){

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new RuntimeException("Order already cancelled");
        }

        // map Order -> OrderRequestDto
        OrderRequestDto dto = modelMapper.map(order, OrderRequestDto.class);

        // call inventory service to restore stock
        inventoryOpenFeignClient.refuelStocks(dto);

        // update order status
        order.setOrderStatus(OrderStatus.CANCELLED);

        Orders updated = orderRepository.save(order);

        return modelMapper.map(updated, OrderRequestDto.class);
    }

    public OrderRequestDto shipOrder(Long orderId){

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new RuntimeException("Cannot ship cancelled order");
        }

        if(order.getOrderStatus() == OrderStatus.SHIPPED){
            throw new RuntimeException("Order already shipped");
        }

        // call shipping service
        shippingOpenFeignClient.confirmShipping(
                modelMapper.map(order, OrderRequestDto.class)
        );

        // update status
        order.setOrderStatus(OrderStatus.SHIPPED);

        Orders updated = orderRepository.save(order);

        return modelMapper.map(updated, OrderRequestDto.class);
    }

}
