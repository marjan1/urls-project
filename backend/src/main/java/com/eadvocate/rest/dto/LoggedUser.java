package com.eadvocate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Helper class for storing and validation of login user data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggedUser {

    private UserDto user;

    private String bearerToken;


}
