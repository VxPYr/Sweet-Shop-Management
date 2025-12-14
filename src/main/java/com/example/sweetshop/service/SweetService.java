package com.example.sweetshop.service;

import com.example.sweetshop.DTO.SweetRequest;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SweetService {

    @Autowired
    private SweetRepository sweetRepository;

    public void addSweet(SweetRequest sweetRequest) {
        validate(sweetRequest);
        Sweet sweet = mapToEntity(sweetRequest);
        sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    public List<Sweet> searchSweets(String name, String category) {

        if (name != null && category != null) {
            return sweetRepository
                    .findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);
        }

        if (name != null) {
            return sweetRepository.findByNameContainingIgnoreCase(name);
        }

        if (category != null) {
            return sweetRepository.findByCategoryIgnoreCase(category);
        }

        return sweetRepository.findAll();
    }

    public Sweet updateSweet(String id, SweetRequest request) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (request.getName() != null) sweet.setName(request.getName());
        if (request.getCategory() != null) sweet.setCategory(request.getCategory());
        if (request.getPrice() != null) sweet.setPrice(request.getPrice());
        if (request.getQuantity() != null) sweet.setQuantity(request.getQuantity());

        return sweetRepository.save(sweet);
    }

    public void deleteSweet(String id) {
        sweetRepository.deleteById(id);
    }

    public Sweet purchaseSweet(String id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (quantity <= 0 || sweet.getQuantity() < quantity) {
            throw new RuntimeException("Invalid or insufficient quantity");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        return sweetRepository.save(sweet);
    }


    public Sweet restockSweet(String id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }


    private void validate(SweetRequest request) {
        if (request.getName() == null || request.getName().isBlank())
            throw new RuntimeException("Name required");
        if (request.getCategory() == null || request.getCategory().isBlank())
            throw new RuntimeException("Category required");
        if (request.getPrice() == null || request.getPrice() <= 0)
            throw new RuntimeException("Invalid price");
        if (request.getQuantity() == null || request.getQuantity() < 0)
            throw new RuntimeException("Invalid quantity");
    }

    private Sweet mapToEntity(SweetRequest request) {
        Sweet sweet = new Sweet();
        sweet.setName(request.getName());
        sweet.setCategory(request.getCategory());
        sweet.setPrice(request.getPrice());
        sweet.setQuantity(request.getQuantity());
        return sweet;
    }
}
