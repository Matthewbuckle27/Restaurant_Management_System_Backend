package com.maveric.restaurantmanagementsystem.controller;

import com.maveric.restaurantmanagementsystem.dto.ProductRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.ProductResponse;
import com.maveric.restaurantmanagementsystem.dto.ProductResponseDTO;
import com.maveric.restaurantmanagementsystem.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductResponse productResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct(){
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder().
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).build();
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();

        when(productService.addProduct(productRequestDTO)).thenReturn(productResponseDTO);
        ResponseEntity<ProductResponse> result = productController.addProduct(productRequestDTO);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void testGetProducts(){
        ProductResponseDTO productResponseDTO1 = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        ProductResponseDTO productResponseDTO2 = ProductResponseDTO.builder().
                productId(1L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
        List<ProductResponseDTO> productResponseDTOS = Arrays.asList(productResponseDTO1, productResponseDTO2);
        when(productService.getProducts()).thenReturn(productResponseDTOS);
        ResponseEntity<List<ProductResponseDTO>> result = productController.getProducts();
        assertNotNull(result);
        assertEquals(productResponseDTOS.size(), Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    void testChangeProductAvailability(){
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(false).build();
        when(productService.changeProductAvailability(anyLong())).thenReturn(productResponseDTO);
        ResponseEntity<ProductResponse> result = productController.changeProductAvailability(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void testUpdateProductPrice(){
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(250.0).isProductAvailable(false).build();
        when(productService.updateProductPrice(anyLong(),anyDouble())).thenReturn(productResponseDTO);
        ResponseEntity<ProductResponse> result = productController.updateProductPrice(1L, 250.0);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result);
    }

}
