package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
/**
 * Dto class for validation and transfer OppositeSideAdvocateDto data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OppositeSideAdvocateDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String address;

    private String phone;

    @NotNull
    private String email;


}
