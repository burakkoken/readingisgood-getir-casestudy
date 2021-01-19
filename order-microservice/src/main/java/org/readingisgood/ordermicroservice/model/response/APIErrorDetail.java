package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

@Data
public class APIErrorDetail {

    private String message;
    private String reason;

    public APIErrorDetail(String message, String reason) {
        this.message = message;
        this.reason = reason;
    }

}
