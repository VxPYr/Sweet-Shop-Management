package com.example.sweetshop.controller;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.service.SweetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    @Autowired
    private SweetService sweetService;

    @PostMapping
    public ResponseEntity<Sweet> createSweet(@RequestBody Sweet sweet) {
        sweetService.addSweet(sweet);
        return new ResponseEntity<>(sweet, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllSweets() {
        List<Sweet> sweetEntries = sweetService.getAllSweets();
        return new ResponseEntity<>(sweetEntries, HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Sweet>> searchSweet(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice
//    ) {
//        sweetService.searchSweetsUsingParam(name, category, minPrice, maxPrice);
//        return new ResponseEntity<>()
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweet> updateSweet(@PathVariable String id, @RequestBody Sweet newIncomingSweet) {
        Sweet oldSweet = sweetService.getSweetById(id).orElse(null);

        if(oldSweet != null) {
            oldSweet.setName(newIncomingSweet.getName() != null && !newIncomingSweet.getName().equals("")? newIncomingSweet.getName() : oldSweet.getName());
            oldSweet.setCategory(newIncomingSweet.getCategory() != null && !newIncomingSweet.getCategory().equals("")? newIncomingSweet.getCategory() : oldSweet.getCategory());
            oldSweet.setPrice(newIncomingSweet.getPrice() != null ? newIncomingSweet.getPrice() : oldSweet.getPrice());
            oldSweet.setQuantity(newIncomingSweet.getQuantity() != null ? newIncomingSweet.getQuantity() : oldSweet.getQuantity());
            sweetService.saveEntry(oldSweet);
            return new ResponseEntity<>(oldSweet, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

