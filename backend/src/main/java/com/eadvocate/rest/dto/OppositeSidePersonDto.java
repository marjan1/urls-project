package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class for validation and transfer OppositeSidePerson data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OppositeSidePersonDto extends OppositeSideDto {

    public String embg;
}
