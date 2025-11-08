package com.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InventoryResponseDTO {

    private String skuCode;
    private Boolean productInStock;
    private Boolean stockDecreased;
    private String message;

}
