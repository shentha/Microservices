package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "price-service",
        url = "${eureka.client.service-url.defaultZone}",
        configuration = PriceClientConfig.class
)
public interface PriceClientReactive {

    @GetMapping("/price/{productid}")
    public Mono<Price> getProductInfo(@PathVariable( name = "productid") Long productId);

}
