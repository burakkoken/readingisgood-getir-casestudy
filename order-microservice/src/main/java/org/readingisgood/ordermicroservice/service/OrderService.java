package org.readingisgood.ordermicroservice.service;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.ordermicroservice.event.OrderEvent;
import org.readingisgood.ordermicroservice.event.OrderEventType;
import org.readingisgood.ordermicroservice.event.StockEvent;
import org.readingisgood.ordermicroservice.event.StockEventType;
import org.readingisgood.ordermicroservice.exception.OrderNotFoundException;
import org.readingisgood.ordermicroservice.exception.StockDataNotObtainedException;
import org.readingisgood.ordermicroservice.exception.StockNotEnoughException;
import org.readingisgood.ordermicroservice.exception.TokenInvalidException;
import org.readingisgood.ordermicroservice.feign.StockClient;
import org.readingisgood.ordermicroservice.model.Order;
import org.readingisgood.ordermicroservice.model.OrderStatus;
import org.readingisgood.ordermicroservice.model.mapper.OrderMapper;
import org.readingisgood.ordermicroservice.model.request.CreateOrderRequest;
import org.readingisgood.ordermicroservice.model.response.BookStockResponse;
import org.readingisgood.ordermicroservice.model.response.OrderDTO;
import org.readingisgood.ordermicroservice.model.response.StockDTO;
import org.readingisgood.ordermicroservice.publisher.OrderEventPublisher;
import org.readingisgood.ordermicroservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderEventPublisher orderEventPublisher;
    private StockClient stockClient;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderMapper orderMapper,
                        OrderEventPublisher orderEventPublisher,
                        StockClient stockClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderEventPublisher = orderEventPublisher;
        this.stockClient = stockClient;
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest, Long userId) {
        if(userId == null) {
            throw TokenInvalidException.of("Token does not have user data");
        }

        boolean hasStock = checkBookStock(createOrderRequest);
        if(!hasStock) {
            throw StockNotEnoughException.of(createOrderRequest.getBookId());
        }

        Order order = orderMapper.toOrder(createOrderRequest);
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        order.setTransactionId(UUID.randomUUID().toString());
        // save
        orderRepository.save(order);

        // publish
        orderEventPublisher.publish(OrderEvent.of(order, OrderEventType.PENDING));
        return orderMapper.toOrderDTO(order);
    }

    private boolean checkBookStock(CreateOrderRequest createOrderRequest) {
        BookStockResponse bookStockResponse = stockClient.getBookStock(createOrderRequest.getBookId());
        if (bookStockResponse == null) {
            throw StockDataNotObtainedException.of(createOrderRequest.getBookId());
        }

        if (Boolean.TRUE.equals(bookStockResponse.getSuccess()) && bookStockResponse.getStockDTO() != null) {
            Order order = orderMapper.toOrder(createOrderRequest);
            StockDTO stockDTO = bookStockResponse.getStockDTO();
            long stockQuantity = stockDTO.getQuantity() - order.getQuantity();
            if (stockQuantity >= 0) {
                return true;
            } else {
                return false;
            }
        }
        throw StockDataNotObtainedException.of(createOrderRequest.getBookId());
    }

    public OrderDTO getOrder(String transactionId, Long userId) {
        if(userId == null) {
            throw TokenInvalidException.of("Token does not have user data");
        }

        Optional<Order> orderOptional = orderRepository.findByTransactionIdAndUserId(transactionId, userId);
        Order order = orderOptional.orElseThrow(() -> OrderNotFoundException.of(transactionId));
        return orderMapper.toOrderDTO(order);
    }

    public List<OrderDTO> getAllOrders(Long userId) {
        if(userId == null) {
            throw TokenInvalidException.of("Token does not have user data");
        }

        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toOrderDTOList(orders);
    }

    @Transactional
    public void markOrderAsCompletedOrCanceled(StockEvent stockEvent) {
        Optional<Order> orderOptional = orderRepository.findByTransactionId(stockEvent.getTransactionId());
        if(orderOptional.isPresent()) {

            Order order = orderOptional.get();
            if(StockEventType.RESERVED == stockEvent.getEventType()) {
                order.setStatus(OrderStatus.COMPLETED);
            } else if (StockEventType.RESERVED_FAILED == stockEvent.getEventType()) {
                order.setStatus(OrderStatus.CANCELLED);
            } else {
                log.debug("Stock Event Type is not valid : {}", stockEvent.toString());
            }
            orderRepository.save(order);

        } else {
            log.debug("Order transaction id was not found : {}", stockEvent.toString());
        }
    }

}
