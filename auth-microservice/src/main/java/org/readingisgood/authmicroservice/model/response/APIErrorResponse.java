package org.readingisgood.authmicroservice.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class APIErrorResponse extends APIResponse {

    private APIError error;

    public APIErrorResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    public APIErrorResponse(HttpStatus status, String message, List<APIErrorDetail> errors) {
        super(false);
        this.error = new APIError(status.value(), message, errors);
    }

}
