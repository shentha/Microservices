package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "inventory-client", url = "http://localhost:8003")
public interface InventoryClient {
    @GetMapping("/inventory/{productid}")
    public Inventory getInventoryInfo(@PathVariable(name = "productid") Long productId);

}
