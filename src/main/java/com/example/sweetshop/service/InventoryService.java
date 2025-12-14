package com.example.sweetshop.service;

import com.example.sweetshop.entity.Purchase;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.PurchaseRepository;
import com.example.sweetshop.repository.SweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InventoryService {

    @Autowired
    private SweetRepository sweetRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public InventoryService(SweetRepository sweetRepository, PurchaseRepository purchaseRepository) {
        this.sweetRepository = sweetRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Sweet purchaseSweet(String sweetId, String userId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        sweetRepository.save(sweet);

        Purchase purchase = new Purchase();
        purchase.setSweetId(sweetId);
        purchase.setUserId(userId);
        purchase.setQuantity(quantity);
        purchase.setTimestamp(LocalDateTime.now());
        purchaseRepository.save(purchase);

        return sweet;
    }

    public Sweet restockSweet(String sweetId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }
}
