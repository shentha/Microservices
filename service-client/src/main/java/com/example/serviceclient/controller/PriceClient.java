package com.example.serviceclient.controller;


import com.example.serviceclient.model.Price;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( value = "price-client", url = "http://localhost:8002")
public interface PriceClient {
    @GetMapping("/price/{productid}")
    public Price getProductInfo(@PathVariable( name = "productid") Long productId);

}
