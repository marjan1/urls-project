package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class for validation and transfer OppositeSideCompanyDto data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OppositeSideCompanyDto extends OppositeSideDto {

    public String embs;

    public String edb;
}
