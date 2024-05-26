package com.projet.demo.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private String itemName;
    private int quantity;
    private Category category;
    private String productNumber;
    private double price ;


    // Other fields as needed
}