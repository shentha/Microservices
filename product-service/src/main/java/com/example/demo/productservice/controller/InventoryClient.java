package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(  name = "gateway-service")
public interface InventoryClient {
    @GetMapping("/inventory/{productid}")
    public Inventory getInventoryInfo(@PathVariable(name = "productid") Long productId);

    @GetMapping ("/inventory/port")
    public String getInventoryDetails();

}
