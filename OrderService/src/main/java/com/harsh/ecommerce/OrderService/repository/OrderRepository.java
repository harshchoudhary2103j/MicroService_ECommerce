package com.harsh.ecommerce.OrderService.repository;

import com.harsh.ecommerce.OrderService.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>{
}
