package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Dto class for validation and transfer Privilege data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto {

    private Long id;

    @Size(max = 100)
    private String name;

    private Set<RoleDto> roleDtos;
}
