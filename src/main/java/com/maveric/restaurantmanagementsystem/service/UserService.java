package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.UserRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO addUser(UserRequestDTO userRequestDTO);
}
