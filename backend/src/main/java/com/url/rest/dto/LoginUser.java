package com.url.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Helper class for storing and validation of login user data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser {

    private String email;

    @Size(min = 5, max = 60)
    private String password;


}
