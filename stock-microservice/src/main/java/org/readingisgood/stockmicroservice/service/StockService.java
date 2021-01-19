package org.readingisgood.stockmicroservice.service;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.stockmicroservice.event.OrderEvent;
import org.readingisgood.stockmicroservice.event.OrderEventType;
import org.readingisgood.stockmicroservice.event.StockEvent;
import org.readingisgood.stockmicroservice.event.StockEventType;
import org.readingisgood.stockmicroservice.exception.StockNotFoundException;
import org.readingisgood.stockmicroservice.model.Stock;
import org.readingisgood.stockmicroservice.model.mapper.StockMapper;
import org.readingisgood.stockmicroservice.model.response.StockDTO;
import org.readingisgood.stockmicroservice.publisher.StockEventPublisher;
import org.readingisgood.stockmicroservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StockService {

    private StockRepository stockRepository;
    private StockMapper stockMapper;
    private StockEventPublisher stockEventPublisher;

    @Autowired
    public StockService(StockRepository stockRepository,
                        StockMapper stockMapper,
                        StockEventPublisher stockEventPublisher) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.stockEventPublisher = stockEventPublisher;
    }

    public StockDTO getBookStock(Long bookId) {
        Optional<Stock> stockOptional = stockRepository.getByBookId(bookId);
        Stock stock = stockOptional.orElseThrow(() -> StockNotFoundException.of(bookId));
        return stockMapper.toStockDTO(stock);
    }

    public List<StockDTO> getAllStock() {
        return stockMapper.toStockDTOList(stockRepository.findAll());
    }

    @Transactional
    public void updateStockQuantity(OrderEvent orderEvent) {
        Optional<Stock> stockOptional = stockRepository.findByBookId(orderEvent.getBookId());
        if(stockOptional.isPresent()) {

            Stock stock = stockOptional.get();
            if(OrderEventType.PENDING == orderEvent.getEventType()) {

                long stockQuantity = stock.getQuantity() - orderEvent.getQuantity();
                if (stockQuantity < 0) {
                    stockEventPublisher.publish(
                            StockEvent.of(orderEvent.getTransactionId(),
                                    StockEventType.RESERVED_FAILED, "Not enough stock quantity")
                    );
                } else {
                    stockEventPublisher.publish(
                            StockEvent.of(orderEvent.getTransactionId(), StockEventType.RESERVED)
                    );
                    stock.setQuantity(stockQuantity);
                    stockRepository.save(stock);
                }

            } else {
                log.debug("OrderEventType is not valid : {}",  orderEvent.toString());
            }

        } else {
            log.debug("Book Id was not found: {}" , orderEvent.toString());
        }
    }

}
