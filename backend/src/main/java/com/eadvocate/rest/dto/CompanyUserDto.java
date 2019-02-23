package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * Dto class for validation and transfer AdvocateCompany data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUserDto {

    @NotNull
    private AdvocateCompanyDto advocateCompany;

    @NotNull
    private UserDto user;
}
