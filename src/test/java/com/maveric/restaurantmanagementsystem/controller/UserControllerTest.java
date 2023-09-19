package com.maveric.restaurantmanagementsystem.controller;

import com.maveric.restaurantmanagementsystem.dto.UserRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.UserResponseDTO;
import com.maveric.restaurantmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_AddUser(){
        UserRequestDTO userRequestDTO = UserRequestDTO.builder().
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        when(userService.addUser(userRequestDTO)).thenReturn(userResponseDTO);
        ResponseEntity<UserResponseDTO> result = userController.addUser(userRequestDTO);
        assertNotNull(result);
    }
}
