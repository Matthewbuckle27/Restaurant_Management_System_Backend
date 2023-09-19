package com.maveric.restaurantmanagementsystem.dto;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = AppConstants.USERNAME_NOTNULL)
    private String username;

    @NotBlank(message = AppConstants.EMAIL_NOTNULL)
    @Email(message = AppConstants.INVALID_EMAIL)
    private String email;

    @NotBlank(message = AppConstants.PASSWORD_NOTNULL)
    @Size(min = 8, message = AppConstants.PASSWORD_LENGTH)
    private String password;

    @NotBlank(message = AppConstants.ROLE_NOTNULL)
    private String role;
}
