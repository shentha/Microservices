package com.example.pricingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Long priceId;
    private Long productID;
    private Integer originalPrice;
    private Integer discountedPrice;
}
