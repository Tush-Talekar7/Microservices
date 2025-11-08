package com.orderservice.controller;

import com.orderservice.constants.OrderConstants;
import com.orderservice.dto.InventoryResponseDTO;
import com.orderservice.model.Order;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<InventoryResponseDTO> placeOrder(@RequestBody Order order){

        InventoryResponseDTO responseDTO = orderService.placeOrder(order);
        if (OrderConstants.SUCCESS.equals(responseDTO.getMessage())){
            return ResponseEntity.ok(responseDTO);
        }else {
            return ResponseEntity.noContent().build();
        }

    }
}
