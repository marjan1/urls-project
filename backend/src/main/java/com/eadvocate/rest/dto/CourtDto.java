package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto class for validation and transfer CourtDto data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourtDto {

    private Long id;

    @NotNull
    private String name;

    private String address;

    @Size(max = 45)
    private String archivePhone;

    @NotNull
    private String archiveEmail;


}

