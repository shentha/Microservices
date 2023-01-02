package com.example.pricingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    private Long id;
    private Currencies from;
    private Currencies to;
    private double exchangeValue;
}
