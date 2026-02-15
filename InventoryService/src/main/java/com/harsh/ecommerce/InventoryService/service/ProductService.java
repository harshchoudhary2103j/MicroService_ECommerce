package com.harsh.ecommerce.InventoryService.service;

import com.harsh.ecommerce.InventoryService.dto.ProductDto;
import com.harsh.ecommerce.InventoryService.entity.Product;
import com.harsh.ecommerce.InventoryService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto>getAllInventory(){
        log.info("Getting all products");
        List<Product>products = productRepository.findAll();

        return products.stream().map((element) -> modelMapper.map(element, ProductDto.class)).collect(Collectors.toList());

    }

    public ProductDto getProductById(Long id){
        log.info("Getting product by id");
       Optional<Product>product = productRepository.findById(id);
        return product.map((element) -> modelMapper.map(element, ProductDto.class)).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }

}
