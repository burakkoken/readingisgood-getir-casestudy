package org.readingisgood.ordermicroservice.feign;

import org.readingisgood.ordermicroservice.model.response.BookStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("readingisgood-stock-microservice")
public interface StockClient {

    @GetMapping("/stock/{bookId}")
    BookStockResponse getBookStock(@PathVariable Long bookId);

}
