package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

import java.util.List;

@Data
public class APIError {

    private int code;
    private String message;
    private List<APIErrorDetail> errors;

    public APIError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public APIError(int code, String message, List<APIErrorDetail> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }
}
