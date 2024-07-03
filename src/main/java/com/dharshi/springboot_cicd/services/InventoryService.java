package com.dharshi.springboot_cicd.services;


import com.dharshi.springboot_cicd.dtos.ApiResponseDto;
import com.dharshi.springboot_cicd.dtos.InventoryRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    ResponseEntity<ApiResponseDto<?>> getAllInventories();

    ResponseEntity<ApiResponseDto<?>> getInventoryById(String inventoryId);

    ResponseEntity<ApiResponseDto<?>> createInventory(InventoryRequestDto inventoryRequestDto);

    ResponseEntity<ApiResponseDto<?>> editInventory(String inventoryId, InventoryRequestDto inventoryRequestDto);

    ResponseEntity<ApiResponseDto<?>> deleteInventory(String inventoryId);
}
