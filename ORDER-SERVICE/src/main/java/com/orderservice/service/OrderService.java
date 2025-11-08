package com.orderservice.service;

import com.orderservice.constants.OrderConstants;
import com.orderservice.dto.InventoryRequestDTO;
import com.orderservice.dto.InventoryResponseDTO;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public String placeOrder(Order order) {
        log.info("Placing order for SKU: {}", order.getSkuCode());

        String url = "http://localhost:9090/api/inventory/isProductInStock";

        // Create request DTO
        InventoryRequestDTO requestDTO =
                new InventoryRequestDTO(order.getSkuCode(), order.getQuantity(), true);

        // Call Inventory Service
        InventoryResponseDTO responseDTO =
                restTemplate.postForObject(url, requestDTO, InventoryResponseDTO.class);

        if (responseDTO == null) {
            log.error("Inventory service did not respond for SKU: {}", order.getSkuCode());
            throw new RuntimeException("Inventory service did not respond");
        }

        log.info("Response from Inventory Service for {}: {}", order.getSkuCode(), responseDTO.getMessage());

        // Check response message and take action
        if (OrderConstants.SUCCESS.equalsIgnoreCase(responseDTO.getMessage())) {
            orderRepository.save(order);
            log.info("Order placed successfully for SKU: {}", order.getSkuCode());
            return "Order placed successfully";
        } else if (OrderConstants.OUT_OF_STOCK.equalsIgnoreCase(responseDTO.getMessage())) {
            throw new RuntimeException("Order not placed — Product is out of stock");
        } else {
            throw new RuntimeException("Order not placed — " + responseDTO.getMessage());
        }
    }
}
