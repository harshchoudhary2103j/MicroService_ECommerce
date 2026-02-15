package com.harsh.ecommerce.InventoryService.repository;

import com.harsh.ecommerce.InventoryService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
