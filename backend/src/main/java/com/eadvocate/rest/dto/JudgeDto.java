package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Dto class for validation and transfer JudgeDto data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeDto {

    private Long id;

    @NotNull
    private String name;

    private String surname;

    private String judgeId;

    @NotNull
    private CourtDto court;

}
