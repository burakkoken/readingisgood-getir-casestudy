package org.readingisgood.ordermicroservice.model.mapper;

import org.mapstruct.Mapper;
import org.readingisgood.ordermicroservice.model.Order;
import org.readingisgood.ordermicroservice.model.request.CreateOrderRequest;
import org.readingisgood.ordermicroservice.model.response.OrderDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOList(List<Order> orderList);

}
