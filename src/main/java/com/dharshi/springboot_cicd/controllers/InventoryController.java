package com.dharshi.springboot_cicd.controllers;

import com.dharshi.springboot_cicd.dtos.ApiResponseDto;
import com.dharshi.springboot_cicd.dtos.InventoryRequestDto;
import com.dharshi.springboot_cicd.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<?>> getAllInventories(){
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponseDto<?>> getInventoryById(@PathVariable String inventoryId){
        return inventoryService.getInventoryById(inventoryId);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<?>> createInventory(@RequestBody InventoryRequestDto inventoryRequestDto){
        return inventoryService.createInventory(inventoryRequestDto);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseDto<?>> editInventory(@RequestParam("inventoryId") String inventoryId, @RequestBody InventoryRequestDto inventoryRequestDto){
        return inventoryService.editInventory(inventoryId, inventoryRequestDto);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponseDto<?>> deleteInventory(@RequestParam("inventoryId") String inventoryId){
        return inventoryService.deleteInventory(inventoryId);
    }
}
