package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Dto class for validation and transfer GovernmentAuthoritiesOppositeSideDto data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GovernmentAuthoritiesOppositeSideDto {

    private Long id;

    @NotNull
    private String name;

    private String address;
}
