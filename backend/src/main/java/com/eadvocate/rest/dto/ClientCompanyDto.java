package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto class for validation and transfer ClientCompany data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyDto extends ClientDto {

    @NotNull
    @Size(max = 7)
    private String embs;

    @Size(max = 13)
    private String edb;

    @Size(max = 100)
    private String managerName;
}
