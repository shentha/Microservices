package com.example.serviceclient.controller;

import com.example.serviceclient.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "inventory-client", url = "http://localhost:8003")
public interface InventoryClient {
    @GetMapping("/inventory/{productid}")
    public Inventory getInventoryInfo(@PathVariable(name = "productid") Long productId);

}
