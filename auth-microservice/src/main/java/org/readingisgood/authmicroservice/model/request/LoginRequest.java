package org.readingisgood.authmicroservice.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

}
