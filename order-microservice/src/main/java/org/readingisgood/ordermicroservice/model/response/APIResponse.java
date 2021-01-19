package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

@Data
public class APIResponse {

    private boolean isSuccess;

    public APIResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
