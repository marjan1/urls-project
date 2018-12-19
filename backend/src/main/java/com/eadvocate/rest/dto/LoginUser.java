package com.eadvocate.rest.dto;

import javax.validation.constraints.Size;

public class LoginUser {

    private String email;

    @Size(min = 5, max = 60)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
