package com.example.sweetshop.repository;

import com.example.sweetshop.entity.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
}
