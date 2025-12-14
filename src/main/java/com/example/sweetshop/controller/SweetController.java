package com.example.sweetshop.controller;

import com.example.sweetshop.DTO.SweetRequest;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import com.example.sweetshop.service.SweetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    @Autowired
    private SweetService sweetService;

    @PostMapping
    public ResponseEntity<SweetRequest> createSweet(@RequestBody SweetRequest sweetRequest) {
        sweetService.addSweet(sweetRequest);
        return new ResponseEntity<>(sweetRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllSweets() {
        List<Sweet> sweetEntries = sweetService.getAllSweets();
        return new ResponseEntity<>(sweetEntries, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweet(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<Sweet> sweets = sweetService.searchSweets(name, category);
        return new ResponseEntity<>(sweets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweet> update(@PathVariable String id, @RequestBody SweetRequest request) {
        return ResponseEntity.ok(sweetService.updateSweet(id, request));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchase(@PathVariable String id, @RequestParam int quantity) {
        return ResponseEntity.ok(sweetService.purchaseSweet(id, quantity));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/restock")
    public ResponseEntity<Sweet> restock(@PathVariable String id, @RequestParam int quantity) {
        return ResponseEntity.ok(sweetService.restockSweet(id, quantity));
    }
}

