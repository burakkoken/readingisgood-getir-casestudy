package org.readingisgood.stockmicroservice.controller;

import org.readingisgood.stockmicroservice.model.response.StockDTO;
import org.readingisgood.stockmicroservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<StockDTO> getBookStock(@PathVariable Long bookId) {
        return ResponseEntity.ok().body(stockService.getBookStock(bookId));
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStock() {
        return ResponseEntity.ok().body(stockService.getAllStock());
    }

}
