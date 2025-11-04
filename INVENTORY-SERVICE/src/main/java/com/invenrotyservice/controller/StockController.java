package com.invenrotyservice.controller;

import com.invenrotyservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class StockController {

    StockService stockService;

    @GetMapping("/isProductInStock")
    public ResponseEntity<Boolean> isProductInStock(@RequestParam  String skuCode){

        return ResponseEntity.ok(stockService.isProductInStock(skuCode));
    }


}
