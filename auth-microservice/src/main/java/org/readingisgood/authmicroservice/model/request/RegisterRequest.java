package org.readingisgood.authmicroservice.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {

    @NotNull
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Min(value = 6, message = "Password must be at least 6 characters")
    private String password;

}
