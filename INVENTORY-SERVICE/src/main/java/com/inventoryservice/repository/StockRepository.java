package com.inventoryservice.repository;

import com.inventoryservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {

    Optional<Stock> findBySkuCode(String skuCode);
}
