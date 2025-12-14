package com.example.sweetshop.service;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.repository.SweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SweetService {
    @Autowired
    private SweetRepository sweetRepository;

    public void addSweet(Sweet sweet) {
        sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

//    public List<Sweet> searchSweetsUsingParam(String name, String category, Double minPrice, Double maxPrice){
//        return sweetRepository.search(name, category, minPrice, maxPrice);
//    }

    public Optional<Sweet> getSweetById(String id) {
        return sweetRepository.findById(id);
    }

    public void saveEntry(Sweet sweet) {
        sweetRepository.save(sweet);
    }
}
