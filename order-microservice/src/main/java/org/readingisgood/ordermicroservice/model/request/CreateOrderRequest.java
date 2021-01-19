package org.readingisgood.ordermicroservice.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrderRequest {

    @NotNull(message = "bookId is mandatory")
    private Long bookId;

    @NotNull(message = "quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;

}
