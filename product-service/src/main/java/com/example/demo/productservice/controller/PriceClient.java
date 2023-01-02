package com.example.demo.productservice.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;
import com.example.demo.productservice.model.Price;

@FeignClient( value = "price-client", url = "http://localhost:8002")
public interface PriceClient {
    @GetMapping("/price/{productid}")
    public  Price getProductInfo(@PathVariable( name = "productid") Long productId);

}
