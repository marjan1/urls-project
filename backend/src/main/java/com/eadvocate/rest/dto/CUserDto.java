package com.eadvocate.rest.dto;


import com.eadvocate.validation.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Dto class for validation and transfer User data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CUserDto {

    private Long id;
    @NotNull
    @Size(min = 1, message = "{Size.userDto.name}")
    private String name;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.surname}")
    private String surname;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private String phone;

    @Singular
    private Set<RoleDto> roles;

    @NotNull
    private StatusDto status;

    private AdvocateCompanyDto advocateCompany;


    @NotNull
    private Short accountGroupLevel;


}
