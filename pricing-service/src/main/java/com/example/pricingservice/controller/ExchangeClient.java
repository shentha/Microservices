package com.example.pricingservice.controller;

import com.example.pricingservice.model.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "exchange-service" )
public interface ExchangeClient {
    @GetMapping("/currexchng/{from}/{to}")
    public Exchange getExchangeInfo(@PathVariable( name = "from") String from, @PathVariable( name = "to") String to);

    }
