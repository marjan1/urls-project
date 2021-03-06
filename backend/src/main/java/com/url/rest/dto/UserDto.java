package com.url.rest.dto;


import com.url.validation.PasswordMatches;
import com.url.validation.ValidEmail;
import com.url.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Dto class for validation and transfer User data.
 */
@Data
@Builder
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotNull
    @Size(min = 1, message = "{Size.userDto.name}")
    private String name;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.surname}")
    private String surname;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private String username;



}
