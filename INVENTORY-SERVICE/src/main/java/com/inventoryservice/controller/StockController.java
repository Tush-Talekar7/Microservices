package com.inventoryservice.controller;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//79659 72424 64747
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/isProductInStock")
    public InventoryResponseDTO isProductInStock(@RequestBody InventoryRequestDTO inventoryRequestDTO){

        return stockService.productInStock(inventoryRequestDTO);
    }




}
