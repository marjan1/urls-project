package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Dto class for validation and transfer AdvocateCompany data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvocateCompanyDto {

    private Long id;

    @NotNull
    private String name;

    private String address;

    @Size(max = 45)
    private String phone;

    @NotNull
    private String email;

    @Size(max = 100)
    private String embs;

    @Size(max = 100)
    private String edbs;

    private String license;

    private String digitalSignature;

    private StatusDto status;
}
