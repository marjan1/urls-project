package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto class for validation and transfer Client data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    @NotNull
    private String name;

    private String address;

    @Size(max = 45)
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private String accountNumber;

    @NotNull
    private AdvocateCompanyDto advocateCompany;
}
