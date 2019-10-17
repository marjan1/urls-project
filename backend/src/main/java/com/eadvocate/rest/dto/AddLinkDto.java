package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Dto class for validation and transfer JudgeDto data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddLinkDto {

    @NotNull
    private String link;
    private List<String> tags;
}
