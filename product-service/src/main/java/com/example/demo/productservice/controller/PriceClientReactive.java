package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Price;
import feign.Param;
import feign.RequestLine;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "gateway-service",
        configuration = PriceClientConfig.class
)
public interface PriceClientReactive {
    //        url = "${eureka.client.service-url.defaultZone}",
    @RequestLine("GET /price/{productid}")
    public Mono<Price> getProductInfo(@Param("productid") Long productId);

}