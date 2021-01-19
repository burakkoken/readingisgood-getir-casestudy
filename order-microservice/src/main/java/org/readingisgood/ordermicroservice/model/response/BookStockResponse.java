package org.readingisgood.ordermicroservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookStockResponse {

    @JsonProperty(value = "success")
    private Boolean success;

    @JsonProperty(value = "data")
    private StockDTO stockDTO;

}
