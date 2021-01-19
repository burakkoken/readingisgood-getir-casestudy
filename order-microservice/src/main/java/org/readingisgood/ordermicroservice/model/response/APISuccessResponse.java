package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

@Data
public class APISuccessResponse extends APIResponse {

    private Object data;

    public APISuccessResponse(Object data) {
        super(true);
        this.data = data;
    }
}
