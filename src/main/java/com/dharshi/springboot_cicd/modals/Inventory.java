package com.dharshi.springboot_cicd.modals;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
@Builder
public class Inventory {

    @Id
    private String itemId;

    private String itemName;

    private double price;

    private int quantity;

    private InventoryStatus status;

}
