package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.UserRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.UserResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.User;
import com.maveric.restaurantmanagementsystem.exception.ServiceException;
import com.maveric.restaurantmanagementsystem.repository.UserRepository;
import com.maveric.restaurantmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder().
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        User user = User.builder().
                id(1L).
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
        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);
        UserResponseDTO result = userService.addUser(userRequestDTO);
        assertNotNull(result);
        assertEquals("Matthew", result.getUsername());
    }

    @Test
    void testAddUser_ServiceException() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder().
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> userService.addUser(userRequestDTO));
    }
}
