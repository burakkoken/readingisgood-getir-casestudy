package org.readingisgood.stockmicroservice;

import org.readingisgood.stockmicroservice.model.Stock;
import org.readingisgood.stockmicroservice.repository.StockRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInit {

    private StockRepository stockRepository;

    public DbInit(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @PostConstruct
    private void initDatabase() {
        stockRepository.save(new Stock(1L, 5L));
        stockRepository.save(new Stock(2L, 12L));
        stockRepository.save(new Stock(3L, 2L));
        stockRepository.save(new Stock(4L, 0L));
    }

}
