package com.example.demo.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productID;
    private String productName;
    private String productDesc;
    private Double productPrice;
    private Boolean productStock;

}
