package com.example.demo.productservice.controller;


import com.example.demo.productservice.model.Price;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "gateway-service" )
public interface PriceClient {
    @GetMapping("/price/{productid}")
    public  Price getProductInfo(@PathVariable( name = "productid") Long productId);

}
