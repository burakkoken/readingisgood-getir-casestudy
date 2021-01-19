package org.readingisgood.stockmicroservice.model.mapper;

import org.mapstruct.Mapper;
import org.readingisgood.stockmicroservice.model.Stock;
import org.readingisgood.stockmicroservice.model.response.StockDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDTO toStockDTO(Stock stock);

    List<StockDTO> toStockDTOList(List<Stock> stockList);

}