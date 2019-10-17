package com.eadvocate.rest.dto;


import com.eadvocate.validation.PasswordMatches;
import com.eadvocate.validation.ValidEmail;
import com.eadvocate.validation.ValidPassword;
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

    private String phone;


    private StatusDto statusDto;



    @NotNull
    private Short accountGroupLevel;


}
