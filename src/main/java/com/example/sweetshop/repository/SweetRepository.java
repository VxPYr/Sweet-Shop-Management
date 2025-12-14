package com.example.sweetshop.repository;

import com.example.sweetshop.entity.Sweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SweetRepository extends MongoRepository<Sweet, String> {
}
