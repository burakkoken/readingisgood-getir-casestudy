package org.readingisgood.stockmicroservice.model.response;

import lombok.Data;

@Data
public class StockDTO {

    private Long bookId;
    private Long quantity;

}
