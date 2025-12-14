package com.example.sweetshop.controller;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/purchase/{id}")
    public ResponseEntity<Sweet> purchaseSweet(
            @PathVariable String id,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {
        Sweet updatedSweet = inventoryService.purchaseSweet(
                id,
                userDetails.getUsername(),
                quantity
        );
        return ResponseEntity.ok(updatedSweet);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/restock/{id}")
    public ResponseEntity<Sweet> restockSweet(
            @PathVariable String id,
            @RequestParam int quantity) {
        return ResponseEntity.ok(inventoryService.restockSweet(id, quantity));
    }
}
