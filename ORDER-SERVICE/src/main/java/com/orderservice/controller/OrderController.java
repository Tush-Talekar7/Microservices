package com.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping("/place-order")
    public ResponseEntity<String> PlaceOrder(){
        return ResponseEntity.ok().body("Order Placed Successfully");
    }
}
