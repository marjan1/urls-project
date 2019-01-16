package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto class for validation and transfer Status data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    private Short id;
    @NotNull
    @Size(min = 1, message = "{Size.statusDto.name}")
    private String name;

    private String description;
}
