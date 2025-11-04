package com.invenrotyservice.service;

import com.invenrotyservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    StockRepository stockRepository;

    public Boolean isProductInStock(String skuCode){
        return stockRepository.findBySkuCode(skuCode)
                .map(stock -> stock.getQuantity() > 0)
                .orElse(Boolean.FALSE);
    }
}
