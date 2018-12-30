package com.eadvocate.rest.dto;


import com.eadvocate.validation.PasswordMatches;
import com.eadvocate.validation.ValidEmail;
import com.eadvocate.validation.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
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
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    @Singular
    private Set<RoleDto> roleDtos;


}
