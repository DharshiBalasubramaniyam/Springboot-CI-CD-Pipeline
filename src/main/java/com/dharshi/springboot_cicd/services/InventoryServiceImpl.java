package com.dharshi.springboot_cicd.services;

import com.dharshi.springboot_cicd.dtos.ApiResponseDto;
import com.dharshi.springboot_cicd.dtos.InventoryRequestDto;
import com.dharshi.springboot_cicd.modals.Inventory;
import com.dharshi.springboot_cicd.modals.InventoryStatus;
import com.dharshi.springboot_cicd.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllInventories(){
        List<Inventory> inventories = inventoryRepository.findAll();
        try {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(inventories)
                            .message(inventories.size() + " results found!")
                            .build()
            );
        }catch (Exception e) {
//            Try to create a custom exception and handle them using exception handlers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .isSuccess(false)
                            .response("Unable to process right now. Try again later!")
                            .message("No results found!")
                            .build()
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getInventoryById(String inventoryId) {

        try {
            Inventory category = inventoryRepository.findById(inventoryId).orElse(null);
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(category)
                            .build()
            );
        }catch (Exception e) {
//            Try to create a custom exception and handle them using exception handlers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .isSuccess(false)
                            .response("Unable to process right now. Try again later!")
                            .message("No results found!")
                            .build()
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> createInventory(InventoryRequestDto inventoryRequestDto) {
        try {

            InventoryStatus status = inventoryRequestDto.getQuantity() > 0 ? InventoryStatus.In_Stock : InventoryStatus.Out_of_Stock;

            Inventory inventory = Inventory.builder()
                    .itemName(inventoryRequestDto.getItemName())
                    .price(inventoryRequestDto.getPrice())
                    .quantity(inventoryRequestDto.getQuantity())
                    .status(status)
                    .build();

            inventoryRepository.insert(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("Inventory saved successfully!")
                        .build()
            );

        }catch (Exception e) {
//            Try to create a custom exception and handle them using exception handlers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .isSuccess(false)
                            .response("Unable to process right now. Try again later!")
                            .message("Unable to create Inventory.")
                            .build()
            );
        }
    }


    @Override
    public ResponseEntity<ApiResponseDto<?>> editInventory(String inventoryId, InventoryRequestDto inventoryRequestDto) {
        try {

            Inventory inventory = inventoryRepository.findById(inventoryId).orElse(null);

            if (inventory == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ApiResponseDto.builder()
                                .isSuccess(false)
                                .message("Inventory not found!")
                                .build()
                );
            }

            InventoryStatus status = inventoryRequestDto.getQuantity() > 0 ? InventoryStatus.In_Stock : InventoryStatus.Out_of_Stock;

            inventory.setItemName(inventoryRequestDto.getItemName());
            inventory.setPrice(inventoryRequestDto.getPrice());
            inventory.setQuantity(inventoryRequestDto.getQuantity());
            inventory.setStatus(status);

            inventoryRepository.save(inventory);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Inventory updated successfully!")
                            .build()
            );

        }catch (Exception e) {
//            Try to create a custom exception and handle them using exception handlers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .isSuccess(false)
                            .response("Unable to process right now. Try again later!")
                            .message("Unable to edit Inventory.")
                            .build()
            );
        }
    }


    @Override
    public ResponseEntity<ApiResponseDto<?>> deleteInventory(String inventoryId) {

        try {
            inventoryRepository.deleteById(inventoryId);
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Inventory deleted successfully!")
                            .build()
            );
        }catch (Exception e) {
//            Try to create a custom exception and handle them using exception handlers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .isSuccess(false)
                            .response("Unable to process right now. Try again later!")
                            .message("No results found!")
                            .build()
            );
        }
    }
}
