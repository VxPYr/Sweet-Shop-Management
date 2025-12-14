package com.example.sweetshop.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "purchases")
@Data
public class Purchase {

    @Id
    private String id;
    private String sweetId;
    private String userId;
    private Integer quantity;
    private LocalDateTime timestamp;
}
