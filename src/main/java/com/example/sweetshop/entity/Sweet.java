package com.example.sweetshop.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sweets")
@Data
public class Sweet {
    @Id
    private String id;

    private String name;

    private String category;

    private Double price;

    private Integer quantity;
}