package com.maveric.restaurantmanagementsystem.service.impl;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import com.maveric.restaurantmanagementsystem.dto.UserRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.UserResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.User;
import com.maveric.restaurantmanagementsystem.exception.ServiceException;
import com.maveric.restaurantmanagementsystem.repository.UserRepository;
import com.maveric.restaurantmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        try {
            User newUser = modelMapper.map(userRequestDTO, User.class);
            User savedUser = userRepository.save(newUser);
            return modelMapper.map(savedUser, UserResponseDTO.class);
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.CREATE_USER_FAILURE);
        }
    }
}
