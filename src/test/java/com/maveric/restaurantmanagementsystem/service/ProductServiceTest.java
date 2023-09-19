package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.ProductRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.ProductResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.Product;
import com.maveric.restaurantmanagementsystem.exception.ProductNotFoundException;
import com.maveric.restaurantmanagementsystem.exception.ServiceException;
import com.maveric.restaurantmanagementsystem.repository.ProductRepository;
import com.maveric.restaurantmanagementsystem.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder().
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).build();
        Product product = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        when(modelMapper.map(productRequestDTO, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(product, ProductResponseDTO.class)).thenReturn(productResponseDTO);
        ProductResponseDTO result = productService.addProduct(productRequestDTO);
        assertNotNull(result);
        assertEquals("Chicken Biryani", result.getProductName());
    }

    @Test
    void testAddProduct_ServiceException() {
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder().
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).build();
        Product product = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        when(modelMapper.map(productRequestDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> productService.addProduct(productRequestDTO));
    }

    @Test
    void testGetProducts() {
        Product product1 = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product product2 = Product.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
        ProductResponseDTO productResponseDTO1 = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        ProductResponseDTO productResponseDTO2 = ProductResponseDTO.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        productResponseDTOS.add(productResponseDTO1);
        productResponseDTOS.add(productResponseDTO2);
        when(productRepository.findAll()).thenReturn(productList);
        when(modelMapper.map(product1, ProductResponseDTO.class)).thenReturn(productResponseDTO1);
        when(modelMapper.map(product2, ProductResponseDTO.class)).thenReturn(productResponseDTO2);
        List<ProductResponseDTO> result = productService.getProducts();
        assertNotNull(result);
        assertEquals(productResponseDTOS, result);
    }

    @Test
    void testGetProducts_ServiceException() {
        when(productRepository.findAll()).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> productService.getProducts());
    }

    @Test
    void testChangeProductAvailability() {
        Product product = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product updatedProduct = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(false).build();
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(false).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when((modelMapper.map(updatedProduct, ProductResponseDTO.class))).thenReturn(productResponseDTO);
        ProductResponseDTO result = productService.changeProductAvailability(anyLong());
        assertFalse(result.isProductAvailable());
    }

    @Test
    void testChangeProductAvailability_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.changeProductAvailability(1L));
    }

    @Test
    void testChangeProductAvailability_ServiceException() {
        when(productRepository.findById(anyLong())).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> productService.changeProductAvailability(1L));
    }

    @Test
    void testUpdatePrice() {
        Double newPrice = 250.0;
        Product product = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product updatedProduct = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(newPrice).isProductAvailable(true).build();
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(newPrice).isProductAvailable(true).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when((modelMapper.map(updatedProduct, ProductResponseDTO.class))).thenReturn(productResponseDTO);
        ProductResponseDTO result = productService.updateProductPrice(anyLong(), newPrice);
        assertEquals(newPrice, result.getPrice());
    }

    @Test
    void testUpdatePrice_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProductPrice(1L,250.0));
    }

    @Test
    void testUpdatePrice_ServiceException() {
        when(productRepository.findById(anyLong())).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> productService.updateProductPrice(1L,250.0));
    }
}
