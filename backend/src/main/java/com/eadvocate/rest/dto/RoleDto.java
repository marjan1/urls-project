package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    private Collection<UserDto> userDtos;

    private Collection<PrivilegeDto> privilegeDtos;


}
