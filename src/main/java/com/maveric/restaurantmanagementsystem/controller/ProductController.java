package com.maveric.restaurantmanagementsystem.controller;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import com.maveric.restaurantmanagementsystem.dto.ProductRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.ProductResponse;
import com.maveric.restaurantmanagementsystem.dto.ProductResponseDTO;
import com.maveric.restaurantmanagementsystem.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping(AppConstants.PRODUCT_MAPPING)
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private ProductResponse productResponse;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.addProduct(productRequestDTO);
        productResponse.setMessage(AppConstants.PRODUCT_ADDED);
        productResponse.setHttpStatus(HttpStatus.CREATED);
        productResponse.setProductResponseDTO(productResponseDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> productResponseDTOS = productService.getProducts();
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }

    @PutMapping(AppConstants.PRODUCT_CHANGE_AVAILABILITY)
    public ResponseEntity<ProductResponse> changeProductAvailability(@PathVariable Long productId) {
        ProductResponseDTO productResponseDTO = productService.changeProductAvailability(productId);
        productResponse.setMessage(AppConstants.PRODUCT_UPDATED);
        productResponse.setHttpStatus(HttpStatus.OK);
        productResponse.setProductResponseDTO(productResponseDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping(AppConstants.PRODUCT_CHANGE_PRICE)
    public ResponseEntity<ProductResponse> updateProductPrice
            (@PathVariable Long productId, @PathVariable Double price) {
        ProductResponseDTO productResponseDTO = productService.updateProductPrice(productId,price);
        productResponse.setMessage(AppConstants.PRODUCT_PRICE_UPDATED);
        productResponse.setHttpStatus(HttpStatus.OK);
        productResponse.setProductResponseDTO(productResponseDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

}
