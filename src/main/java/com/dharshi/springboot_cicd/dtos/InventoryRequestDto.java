package com.dharshi.springboot_cicd.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDto {

    private String itemName;

    private double price;

    private int quantity;

}
