package org.readingisgood.authmicroservice.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APISuccessResponse extends APIResponse {

    private Object data;

    public APISuccessResponse(Object data) {
        super(true);
        this.data = data;
    }
}
