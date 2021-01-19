package org.readingisgood.ordermicroservice.controller;

import org.readingisgood.ordermicroservice.model.request.CreateOrderRequest;
import org.readingisgood.ordermicroservice.model.response.OrderDTO;
import org.readingisgood.ordermicroservice.service.OrderService;
import org.readingisgood.ordermicroservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Version;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OrderController(OrderService orderService, JwtTokenUtil jwtTokenUtil) {
        this.orderService = orderService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest, @Valid @RequestHeader (name="Authorization") String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderRequest, userId));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable String transactionId, @Valid @RequestHeader (name="Authorization") String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        return ResponseEntity.ok().body(orderService.getOrder(transactionId, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(@Valid @RequestHeader (name="Authorization") String token) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        return ResponseEntity.ok().body(orderService.getAllOrders(userId));
    }

}
