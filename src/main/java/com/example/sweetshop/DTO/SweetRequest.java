package com.example.sweetshop.DTO;

import lombok.Data;

@Data
public class SweetRequest {
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
}
