package com.eadvocate.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class for validation and transfer ClientPerson data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPersonDto extends ClientDto {

    private String personalId;

    private String embg;
}
