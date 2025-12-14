package com.example.sweetshop.repository;

import com.example.sweetshop.entity.Sweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SweetRepository extends MongoRepository<Sweet, String> {

    List<Sweet> findByNameContainingIgnoreCase(String name);

    List<Sweet> findByCategoryIgnoreCase(String category);

    List<Sweet> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(
            String name,
            String category
    );
}