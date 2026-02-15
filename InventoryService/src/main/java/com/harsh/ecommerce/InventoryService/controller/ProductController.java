package com.harsh.ecommerce.InventoryService.controller;

import com.harsh.ecommerce.InventoryService.dto.ProductDto;
import com.harsh.ecommerce.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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

}
