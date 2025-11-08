package com.inventoryservice.service;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.entity.Stock;
import com.inventoryservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public InventoryResponseDTO productInStock(InventoryRequestDTO inventoryRequestDTO) {
        log.info("Entered in productInStock: {}", inventoryRequestDTO.getSkuCode());

        String skuCode = inventoryRequestDTO.getSkuCode();
        int requestedQty = inventoryRequestDTO.getQuantity();
        boolean decreaseFlag = Boolean.TRUE.equals(inventoryRequestDTO.getDecreaseFlag());

        Stock stock = stockRepository.findBySkuCode(skuCode).orElse(null);
        if (stock == null) {
            log.warn("Product not found: {}", skuCode);
            return new InventoryResponseDTO(skuCode, false, false, "Product not found");
        }


        if (stock.getQuantity() < requestedQty) {
            log.warn("Out of stock for: {}", skuCode);
            return new InventoryResponseDTO(skuCode, false, false, "Out of stock");
        }

        if (decreaseFlag) {
            boolean decreased = decreaseTheStock(stock, requestedQty);
            log.info("Stock decreased status for {} : {}", skuCode, decreased);

            if (decreased) {
                return new InventoryResponseDTO(skuCode, true, true, "SUCCESS");
            } else {
                return new InventoryResponseDTO(skuCode, true, false, "Failed to decrease stock");
            }
        }

        log.info("Completed productInStock for: {}", skuCode);
        return new InventoryResponseDTO(skuCode, true, false, "AVAILABLE");
    }

    @Transactional
    protected boolean decreaseTheStock(Stock stock, int quantity) {
        log.info("Entered decreaseTheStock: {}", stock.getSkuCode());

        if (stock.getQuantity() < quantity) {
            return false;
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        log.info("Stock successfully decreased for: {}, remaining: {}",
                stock.getSkuCode(), stock.getQuantity());

        return true;
    }
}
